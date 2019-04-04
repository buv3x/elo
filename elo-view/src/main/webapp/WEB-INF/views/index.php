<?
require($_SERVER["DOCUMENT_ROOT"]."/bitrix/header.php");
$APPLICATION->SetTitle("Эло");
?>

<script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.3.1.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.7.3/Chart.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jsgrid/1.5.3/jsgrid.min.js"></script>
<meta charset="UTF-8">
<title>Рейтинг</title>
<link type="text/css" rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jsgrid/1.5.3/jsgrid.min.css" />
<link type="text/css" rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jsgrid/1.5.3/jsgrid-theme.min.css" />
<link rel="stylesheet" href="http://185.179.82.219:8080/elo-view/resources/styles/elo.css">

<?php
switch ($_GET["page"]) {
	case 'detail':
?>
<!-- Page Person Details -->
<ul class="elo-menu">
    <li class="elo-menu-item"><a href="/rating/elo">Рейтинг</a></li>
    <li class="elo-menu-item"><a href="/rating/elo?page=competitions">Старты</a></li>
	<li class="elo-menu-item"><a href="/rating/elo?page=about">О рейтинге</a></li>
</ul>
<div id="filter">
    <table>
        <tr>
            <td >
                <span id="displayName"></span>
            </td>
        </tr>
        <tr>
            <td>
                <select id="type" onchange="reload()">
                </select>
            </td>
        </tr>
    </table>
</div>
<div style="width:800px" id="graphDiv">
    <canvas id="graph"/>
</div>
<div id="ratingGrid"></div>
</body>
<script>

    var id = <?=empty($_GET["id"])?0:htmlspecialchars($_GET["id"])?>;
    var displayName;
    var lineChart;

    $.get( "http://185.179.82.219:8080/elo-api/rest/core/getTypes",
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
            { name: "name", type: "text", width: 250, title:"Старт",
                cellRenderer: function(item, value){
                    return "<td class='jsgrid-cell' style='width:250px'><a href='?page=competition&id=" + value.id + "'>" + item + "</a></td>";
                }},
            { name: "date", type: "text", width: 100, title:"Дата",
                cellRenderer: function(item, value){
                    return "<td class='jsgrid-cell jsgrid-align-right' style='width:150px'><span>" + formatDate(item) + "</span></td>";
                } },
            { name: "type", type: "number", width: 100, title:"Тип" },
            { name: "factorChange", type: "number", width: 100, title:"Коэффициент" },
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
            { name: "place", type: "text", width: 150, title:"Место (Из)", sorting: false,
                cellRenderer: function(item, value){
                    return "<td class='jsgrid-cell jsgrid-align-right' style='width:150px'><span>" + item + " (" + value.placeCount + ")</span></td>";
                }}]
    });

    $.get( 'http://185.179.82.219:8080/elo-api/rest/report/getPersonName?id=' + id,
            function( data ) {
                displayName = data;
                $('#displayName').html(displayName);
                initChart();
                reloadResults('ALL');
            }, 'text'
            );

    function reloadResults(type) {
        var url = 'http://185.179.82.219:8080/elo-api/rest/report/getPersonGraphDetails?id=' + id;
        if(type != 'ALL') {
            url += '&type=' + type;
        }
        $.get( url,
                function( data ) {

                    lineChart.data.labels = [];
                    lineChart.data.datasets.forEach(function(dataset) {
                        dataset.data = [];
                    });

                    data.forEach(function(element) {
                        lineChart.data.labels.push(element.date);
                        lineChart.data.datasets.forEach(function(dataset) {
                            dataset.data.push(element.rating);
                        });
                    });

                    lineChart.update();
                });

        var url = 'http://185.179.82.219:8080/elo-api/rest/report/getPersonDetails?id=' + id;
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

    function initChart() {
        var ctx = $("#graph");
        lineChart = new Chart(ctx, {
            type: 'line',
            data: {
                datasets: [{
                    label: displayName
                }]
            },
            options: {
                scales: {
                    xAxes: [{
                        ticks: {
                            callback: function(value, index, values) {
                                return formatDate(value);
                            },
                            stepSize: 10
                        }
                    }]
                }
            }
        });
    }

	function formatDate(value) {
		var date = new Date(value);
		return date.getFullYear() + "-" + ("0"+(date.getMonth()+1)).slice(-2) + "-" + ("0" + date.getDate()).slice(-2);
	}

</script>

<?php
	break;
	case 'competitions':
?>
<!-- Page Competitions -->
<ul class="elo-menu">
    <li class="elo-menu-item"><a href="/rating/elo">Рейтинг</a></li>
    <li class="elo-menu-item"><a class="active" href="/rating/elo?page=competitions">Старты</a></li>
	<li class="elo-menu-item"><a href="/rating/elo?page=about">О рейтинге</a></li>
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
                        return "<td class='jsgrid-cell' style='width:250px'><a href='?page=competition&id=" + value.id + "'>" + item + "</a></td>";
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
        var url = 'http://185.179.82.219:8080/elo-api/rest/report/getCompetitions';
        $.get( url,
                function( data ) {
                    results = [];
                    data.forEach(function(element) {
                        results.push(element);
                    });
                    $("#ratingGrid").jsGrid("option", "data", results);
                });
    }

	function formatDate(value) {
		var date = new Date(value);
		return date.getFullYear() + "-" + ("0"+(date.getMonth()+1)).slice(-2) + "-" + ("0" + date.getDate()).slice(-2);
	}	

</script>

<?php
	break;
	case 'competition':
?>
<!-- Page Competition Details -->
<ul class="elo-menu">
    <li class="elo-menu-item"><a href="/rating/elo">Рейтинг</a></li>
    <li class="elo-menu-item"><a href="/rating/elo?page=competitions">Старты</a></li>
	<li class="elo-menu-item"><a href="/rating/elo?page=about">О рейтинге</a></li>
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

    var id = <?=empty($_GET["id"])?0:htmlspecialchars($_GET["id"])?>;
    var displayName;
    var lineChart;

    $.get( "http://185.179.82.219:8080/elo-api/rest/report/getCompetitionType?id=" + id,
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
                    return "<td class='jsgrid-cell' style='width:250px'><a href='?page=detail&id=" + value.id + "'>" + item + "</a></td>";
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

    $.get( 'http://185.179.82.219:8080/elo-api/rest/report/getCompetitionName?id=' + id,
            function( data ) {
                displayName = data.name + " " + formatDate(data.date) + " (" + data.gender + ")";
                $('#displayName').html(displayName);
                reloadResults('ALL');
            });

    function reloadResults(type) {
        var url = 'http://185.179.82.219:8080/elo-api/rest/report/getCompetitionDetails?id=' + id;
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

	function formatDate(value) {
		var date = new Date(value);
		return date.getFullYear() + "-" + ("0"+(date.getMonth()+1)).slice(-2) + "-" + ("0" + date.getDate()).slice(-2);
	}	

</script>

<?php
	break;
	case 'about':
?>
<!-- Page About -->
<ul class="elo-menu">
    <li class="elo-menu-item"><a href="/rating/elo">Рейтинг</a></li>
    <li class="elo-menu-item"><a href="/rating/elo?page=competitions">Старты</a></li>
    <li class="elo-menu-item"><a class="active" href="/rating/elo?page=about">О рейтинге</a></li>
</ul>
    Техническое описание рейтинга можно найти тут: <a href="https://mem-maps.blogspot.com/2019/01/1.html">1</a>, <a href="https://mem-maps.blogspot.com/2019/01/2.html">2</a>, <a href="https://mem-maps.blogspot.com/2019/01/3.html">3</a>.
    <br/>
    <br/>
    Страница рейтинга отображает текущий рейтинг спортсменов и изменение рейтинга за период. Период по умолчанию составляет 365 дней, спортсмен отображается на странице, если сумма коэфициентов его стартов за период составляет не менее 80 (для рейтингов по дисциплинам - не менее 40).
    <br/>
    Старты в календаре разделяются на 4 уровня: национальный, БФО, региональный и местный, с соответствующими коэфициентами 40, 30, 20 и 10.
    <br/>
    Имя спортсмена - ссылка на детали рейтинга по спортсмену.
    <br/>
    <br/>
    Страница деталей рейтинга по спортсмену отображает график изменения рейтинга (последние 100 стартов), а также таблицу всех стартов.
    <br/>
    <br/>
    Страница стартов отображает все рассчитанные старты, название стартов является ссылкой на детали рейтинга по старту (в редких случаях она может быть неактивной - это означает, что результаты соревнований внесены в систему, но рейтинг по ним еще не рассчитан).
    <br/>
    <br/>
    Страница деталей рейтинга по старту отображает изменения рейтинга каждого из участвовавших спортсменов.
    <br/>
    <br/>
	Данный рейтинг не является официальным рейтингом БФО.
	<br/>
    <br/>
    Вопросы и предложения по рейтингу можете присылать на bfa.cup.manager@gmail.com
    <br/>
    Мемелов Алексей


<?php
	break;
	default:
?>
<!-- Page Rating -->
<ul class="elo-menu">
    <li class="elo-menu-item"><a class="active" href="/rating/elo">Рейтинг</a></li>
    <li class="elo-menu-item"><a href="/rating/elo?page=competitions">Старты</a></li>
	<li class="elo-menu-item"><a href="/rating/elo?page=about">О рейтинге</a></li>
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
<script>

	$.get( "http://185.179.82.219:8080/elo-api/rest/core/getGenders",
            function( data ) {
                var select = $('#gender');
                data.forEach(function(element) {
                    $('#gender').append(new Option(element.displayName, element.name));
                });
            });

    $.get( "http://185.179.82.219:8080/elo-api/rest/core/getTypes",
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
                    return "<td class='jsgrid-cell' style='width:250px'><a href='?page=detail&id=" + value.id + "'>" + item + "</a></td>";
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
		
        var url = 'http://185.179.82.219:8080/elo-api/rest/report/getRatingList?gender=' + gender;
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
<?php
	break;
}

?>





<?require($_SERVER["DOCUMENT_ROOT"]."/bitrix/footer.php");?>