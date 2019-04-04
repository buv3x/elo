<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head lang="en">
    <script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.3.1.min.js"></script>
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jsgrid/1.5.3/jsgrid.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/scripts/elo.js"></script>
    <meta charset="UTF-8">
    <title>Старты</title>
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
    <li class="elo-menu-item"><a class="active" href="/elo-view/competitions">Старты</a></li>
    <li class="elo-menu-item"><a href="/elo-view/about">О рейтинге</a></li>
</ul>
<div id="ratingGrid"></div>
</body>
<script>

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
            { name: "name", type: "text", width: 250, title:"Старт",
                cellRenderer: function(item, value){
                    if(value.calculated) {
                        return "<td class='jsgrid-cell' style='width:250px'><a href='/elo-view/competition/" + value.id + "'>" + item + "</a></td>";
                    } else {
                        return "<td class='jsgrid-cell' style='width:250px'>" + item + "</td>";
                    }

                }},
            { name: "date", type: "text", width: 100, title:"Дата",
                cellRenderer: function(item, value){
                    return "<td class='jsgrid-cell jsgrid-align-right' style='width:150px'><span>" + formatDate(item) + "</span></td>";
                } },
            { name: "gender", type: "text", width: 100, title:"Пол" },
            { name: "type", type: "text", width: 100, title:"Тип" },
            { name: "factor", type: "number", width: 100, title:"Коэффициент" },
            { name: "resultsCount", type: "number", width: 150, title:"Кол-во участников"}]
    });

    reloadResults();

    function reloadResults() {
        var url = '/elo-api/rest/report/getCompetitions';
        $.get( url,
                function( data ) {
                    results = [];
                    data.forEach(function(element) {
                        results.push(element);
                    });
                    $("#ratingGrid").jsGrid("option", "data", results);
                });
    }

</script>
</html>