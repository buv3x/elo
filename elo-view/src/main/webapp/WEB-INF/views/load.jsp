<!DOCTYPE html>
<html>
<head lang="en">
    <script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.3.1.min.js"></script>
    <meta charset="UTF-8">
    <title>Load Competition</title>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <link rel='stylesheet' href='https://fonts.googleapis.com/css?family=Roboto'>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <style>
        html,body,h1,h2,h3,h4,h5,h6 {font-family: "Roboto", sans-serif}
    </style>
</head>
<body>
<div id="step1">
    <table>
        <tr>
            <td>
                Source
            </td>
            <td>
                <select id="source">
                </select>
            </td>
        </tr>
        <tr>
            <td colspan="2">
			<textarea id="results">
			</textarea>
            </td>
        </tr>
        <tr>
            <td>
                Gender
            </td>
            <td>
                <select id="gender">
                </select>
            </td>
        </tr>
        <tr>
            <td>
                Date
            </td>
            <td>
                <input type="text" id="date">
                </input>
            </td>
        </tr>
        <tr>
            <td>
                Competition
            </td>
            <td>
                <input type="text" id="competition">
                </input>
            </td>
        </tr>
        <tr>
            <td>
                Level
            </td>
            <td>
                <select id="level">
                </select>
            </td>
        </tr>
        <tr>
            <td>
                Type
            </td>
            <td>
                <select id="type">
                </select>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <input type="button" value="Parse & Save" onclick="parseData()">
                </input>
            </td>
        </tr>
    </table>
</div>
<div id="step2" style="display:none">
    <input type="hidden" id="competitionId"/>
    <input type="hidden" id="competitionType"/>
    <input type="hidden" id="competitionLevel"/>
    <input type="hidden" id="competitionName"/>
    <input type="hidden" id="competitionDate"/>
    <input type="hidden" id="competitionGender"/>
    <table id="resultsTable">
        <tr>
            <td>
                New
            </td>
            <td>
                Surname
            </td>
            <td>
                Name
            </td>
            <td>
                Person
            </td>
            <td>
                Place
            </td>
        <tr>
    </table>
    <input type="button" value="Save" onclick="saveResults()">
    </input>
</div>
</body>
<script>

    $.get( "/elo-api/rest/parser/getSources",
            function( data ) {
                var select = $('#source');
                data.forEach(function(element) {
                    $('#source').append(new Option(element.displayName, element.name));
                });
            });

    $.get( "/elo-api/rest/core/getGenders",
            function( data ) {
                var select = $('#gender');
                data.forEach(function(element) {
                    $('#gender').append(new Option(element.displayName, element.name));
                });
            });

    $.get( "/elo-api/rest/core/getLevels",
            function( data ) {
                var select = $('#level');
                data.forEach(function(element) {
                    $('#level').append(new Option(element.displayName, element.name));
                });
            });

    $.get( "/elo-api/rest/core/getTypes",
            function( data ) {
                var select = $('#type');
                data.forEach(function(element) {
                    $('#type').append(new Option(element.displayName, element.name));
                });
            });

    var persons;

    function parseData() {
        var results = $("#results").val();
        var source = $("#source").val();

        var type = $("#type").val();
        var gender = $("#gender").val();
        var level = $("#level").val();
        var name = $("#competition").val();
        var date = $("#date").val();

        $("#step1").hide();

        $.ajax( {
            type: 'post',
            url: "/elo-api/rest/competition/saveCompetition",
            data: JSON.stringify({competition:{type: type, gender: gender, level: level, name: name, date: date},
                parseRequest:{data: results, source: source}}),
            contentType: "application/json; charset=utf-8",
            success: function( data ) {
                $("#competitionType").val(type);
                $("#competitionLevel").val(level);
                $("#competitionName").val(name);
                $("#competitionDate").val(date);
                $("#competitionGender").val(data.gender);
                fillResultsTable(data);
                $.get( "/elo-api/rest/competition/getPersons?gender=" + gender,
                    function( data ) {
                        persons = data;
                        fillPersons();
                        $("#step2").show();
                    });
            },
            error: function() {
                alert("Something went wrong");
            }
        });
    }

    function fillResultsTable(data) {
        var resultsTable = $("#resultsTable");
        data.personResults.forEach(function(result) {
            var row = "<tr class='resultSpan'>" +
                    "<td><input type='checkbox' class='savePerson' />" +
                    "<input type='hidden' class='personId' value='" + result.id + "'/></td>" +
                    "<td><input type='text' class='surname' value='" + result.surname + "'/></td>" +
                    "<td><input type='text' class='name' value='" + result.name + "'/></td>" +
                    "<td><select class='person'></select></td>" +
                    "<td><input type='text' class='place' value='" + result.place + "'/></td></tr>";
            resultsTable.append(row);
        });

    }

    function fillPersons() {
        var resultsTr = $(".resultSpan");
        resultsTr.each(function( index ) {
            var personSelect = $(this).find('.person').first();
            personSelect.append(new Option("---", 0));
            persons.forEach(function(element) {
                personSelect.append(new Option(element.surname + " " + element.name, element.id));
            });
            var personId = $(this).find('.personId').first().val();
            if(personId > 0) {
                personSelect.val(personId);
            } else {
                $(this).find('.savePerson').first().prop('checked', true);
            }
        });
    }

    function saveResults() {
        var resultsTr = $(".resultSpan");
        var rows = [];
        resultsTr.each(function( index ) {
            rowData = {save: $(this).find('.savePerson').first().prop('checked'),
                surname: $(this).find('.surname').first().val(),
                name: $(this).find('.name').first().val(),
                place: $(this).find('.place').first().val(),
                id: $(this).find('.person').first().val()};
            rows.push(rowData);
        });
        var request = {competition:{type: $("#competitionType").val(),
                level: $("#competitionLevel").val(),
                name: $("#competitionName").val(),
                date: $("#competitionDate").val(),
                gender: $("#competitionGender").val()},
            results: rows};

        $.ajax( {
            type: 'post',
            url: "/elo-api/rest/competition/saveResults",
            data: JSON.stringify(request),
            contentType: "application/json; charset=utf-8",
            success: function( data ) {
                window.location.assign("/elo-view/load")
            }
        });
    }

</script>
</html>