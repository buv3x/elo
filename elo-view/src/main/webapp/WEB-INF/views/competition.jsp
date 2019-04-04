<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head lang="en">
    <script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.3.1.min.js"></script>
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jsgrid/1.5.3/jsgrid.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/scripts/elo.js"></script>
    <meta charset="UTF-8">
    <title>Рейтинг по старту</title>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <link rel='stylesheet' href='https://fonts.googleapis.com/css?family=Roboto'>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link type="text/css" rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jsgrid/1.5.3/jsgrid.min.css" />
    <link type="text/css" rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jsgrid/1.5.3/jsgrid-theme.min.css" />
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/styles/elo.css" />
</head>
<body>
<ul class="elo-menu">
    <li class="elo-menu-item"><a href="/elo-view/rating">Рейтинг</a></li>
    <li class="elo-menu-item"><a href="/elo-view/competitions">Старты</a></li>
    <li class="elo-menu-item"><a href="/elo-view/about">О рейтинге</a></li>
</ul>
<div id="filter">
    <table>
        <tr>
            <td >
                <span id="displayName"></span>
            </td>
        </tr>
        <tr>
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

    var id = ${id};
    var displayName;
    var lineChart;

    $.get( "/elo-api/rest/report/getCompetitionType?id=" + id,
            function( data ) {
                var select = $('#type');
                $('#type').append(new Option('Общий', 'ALL'));
                $('#type').append(new Option(data.displayName, data.name));
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
            { name: "name", type: "text", width: 250, title:"Спортсмен",
                cellRenderer: function(item, value){
                    return "<td class='jsgrid-cell' style='width:250px'><a href='/elo-view/detail/" + value.id + "'>" + item + "</a></td>";
                }},
            { name: "rating", type: "number", width: 150, title:"Рейтинг" },
            { name: "ratingChange", type: "text", width: 150, title:"Изменение рейтинга",
                cellRenderer: function(item, value){
                    if(item > 0) {
                        return "<td class='jsgrid-cell jsgrid-align-right' style='width:150px'><span style='color:green'>+" + item + "</span></td>";
                    } else if (item < 0) {
                        return "<td class='jsgrid-cell jsgrid-align-right' style='width:150px'><span style='color:red'>" + item + "</span></td>";
                    } else {
                        return "<td class='jsgrid-cell jsgrid-align-right' style='width:150px'><span>" + item + "</span></td>";
                    }
                }},
            { name: "place", type: "number", width: 100, title:"Место"}]
    });

    $.get( '/elo-api/rest/report/getCompetitionName?id=' + id,
            function( data ) {
                displayName = data.name + " " + formatDate(data.date) + " (" + data.gender + ")";
                $('#displayName').html(displayName);
                reloadResults('ALL');
            });

    function reloadResults(type) {
        var url = '/elo-api/rest/report/getCompetitionDetails?id=' + id;
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
        reloadResults(type);
    }

//    function formatDate(value) {
//        var date = new Date(value);
//        return date.getFullYear() + "-" + ("0"+(date.getMonth()+1)).slice(-2) + "-" + ("0" + date.getDate()).slice(-2);
//    }


</script>
</html>