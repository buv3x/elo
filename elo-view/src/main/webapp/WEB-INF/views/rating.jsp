<!DOCTYPE html>
<html>
<head lang="en">
    <script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.3.1.min.js"></script>
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jsgrid/1.5.3/jsgrid.min.js"></script>
    <meta charset="UTF-8">
    <title>Rating</title>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <link rel='stylesheet' href='https://fonts.googleapis.com/css?family=Roboto'>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link type="text/css" rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jsgrid/1.5.3/jsgrid.min.css" />
    <link type="text/css" rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jsgrid/1.5.3/jsgrid-theme.min.css" />
    <style>
        html,body,h1,h2,h3,h4,h5,h6 {font-family: "Roboto", sans-serif}
        table.list {border-collapse: collapse; border: 1px solid black;}
        table.list th, table.list td {border: 1px solid black; text-align:center}
    </style>
</head>
<body>
<div id="filter">
    <table>
        <tr>
            <td>
                <select id="gender" onchange="reload()">
                </select>
            </td>
            <td >
                <select id="type" onchange="reload()">
                </select>
            </td>
        </tr>
    </table>
</div>
<div id="ratingGrid"></div>
</body>
<script>

    $.get( "/elo-api/rest/core/getGenders",
            function( data ) {
                var select = $('#gender');
                data.forEach(function(element) {
                    $('#gender').append(new Option(element.displayName, element.name));
                });
            });

    $.get( "/elo-api/rest/core/getTypes",
            function( data ) {
                var select = $('#type');
                $('#type').append(new Option('All', 'ALL'));
                data.forEach(function(element) {
                    $('#type').append(new Option(element.displayName, element.name));
                });
            });

    $("#ratingGrid").jsGrid({
        width: "100%",
        //height: "400px",
        inserting: false,
        editing: false,
        sorting: true,
        selecting: false,
        paging: true,
        filtering: false,
        pageSize: 50,
        fields: [
            { name: "displayName", type: "text", width: 250, title:"Name",
                cellRenderer: function(item, value){
                    return "<td class='jsgrid-cell' style='width:250px'><a href='/elo-view/detail/" + value.id + "'>" + item + "</a></td>";
                }},
            { name: "rating", type: "number", width: 150, title:"Rating" },
            { name: "lastYearChange", type: "number", width: 150, title:"Last Year Change" },
            { name: "factor", type: "number", width: 150, title:"Total Factor" },
            { name: "lastYearFactor", type: "number", width: 150, title:"Last Year Factor" }]
    });

    var results = [];

    reloadResults('MEN', 'ALL');

    function reloadResults(gender, type) {
        var url = '/elo-api/rest/report/getRatingList?gender=' + gender;
        if(type != 'ALL') {
            url += '&type=' + type;
        }
        $.get( url,
                function( data ) {
                    results = [];
                    data.forEach(function(element) {
                        results.push(element);
                    });
                    $("#ratingGrid").jsGrid("option", "data", results);
                });
    }

    function reload() {
        var type = $("#type").val();
        var gender = $("#gender").val();
        reloadResults(gender, type);
    }


</script>
</html>