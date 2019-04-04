<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head lang="en">
    <script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.3.1.min.js"></script>
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jsgrid/1.5.3/jsgrid.min.js"></script>
    <meta charset="UTF-8">
    <title>Рейтинг</title>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <link rel='stylesheet' href='https://fonts.googleapis.com/css?family=Roboto'>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link type="text/css" rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jsgrid/1.5.3/jsgrid.min.css" />
    <link type="text/css" rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jsgrid/1.5.3/jsgrid-theme.min.css" />
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/styles/elo.css" />
</head>
<body>
<ul class="elo-menu">
    <li class="elo-menu-item"><a class="active" href="/elo-view/rating">Рейтинг</a></li>
    <li class="elo-menu-item"><a href="/elo-view/competitions">Старты</a></li>
    <li class="elo-menu-item"><a href="/elo-view/about">О рейтинге</a></li>
</ul>
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
    <table>
        <tr>
            <td>
                Период <input type="text" id="period" value="365" maxlength="4" size="4"/> дней.
            </td>
            <td>
                Минимальный коэффициент за период - <input type="text" id="factor" value="80" maxlength="4" size="4"/>.
            </td>
            <td>
                <input type="button" id="apply" onclick="apply()" value="Применить"/>
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
                $('#type').append(new Option('Общий', 'ALL'));
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
            { name: "place", type: "number", width: 50, title:"Место" },
            { name: "displayName", type: "text", width: 250, title:"Спортсмен",
                cellRenderer: function(item, value){
                    return "<td class='jsgrid-cell' style='width:250px'><a href='/elo-view/detail/" + value.id + "'>" + item + "</a></td>";
                }},
            { name: "rating", type: "number", width: 150, title:"Рейтинг" },
            { name: "lastYearChange", type: "number", width: 150, title:"Изменение за период",
                cellRenderer: function(item, value){
                    if(item > 0) {
                        return "<td class='jsgrid-cell jsgrid-align-right' style='width:150px'><span style='color:green'>+" + item + "</span></td>";
                    } else if (item < 0) {
                        return "<td class='jsgrid-cell jsgrid-align-right' style='width:150px'><span style='color:red'>" + item + "</span></td>";
                    } else {
                        return "<td class='jsgrid-cell jsgrid-align-right' style='width:150px'><span>" + item + "</span></td>";
                    }
                } },
            { name: "factor", type: "number", width: 150, title:"Суммарный коэффициент" },
            { name: "lastYearFactor", type: "number", width: 150, title:"Коэффициент за период" }]
    });

    var results = [];

    reloadResults('MEN', 'ALL');

    function reloadResults(gender, type) {
        var period = $("#period").val();
        var factor = $("#factor").val();

        if(isNaN(period) || period < 1 || period > 999999) {
            period = 365;
            $("#period").val(365);
        }

        if(isNaN(factor) || factor < 1 || factor > 999999) {
            if(type != 'ALL') {
                factor = 40;
                $("#factor").val(40);
            } else {
                factor = 80;
                $("#factor").val(80);
            }
        }

        var url = '/elo-api/rest/report/getRatingList?gender=' + gender;
        if(type != 'ALL') {
            url += '&type=' + type;
        }
        url += '&period=' + period;
        url += '&factor=' + factor;
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
        $("#period").val(365);
        if(type != 'ALL') {
            $("#factor").val(40);
        } else {
            $("#factor").val(80);
        }

        reloadResults(gender, type);
    }

    function apply() {
        var type = $("#type").val();
        var gender = $("#gender").val();
        reloadResults(gender, type);
    }


</script>
</html>