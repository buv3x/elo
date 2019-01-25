<!DOCTYPE html>
<html>
<head lang="en">
    <script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.3.1.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.7.3/Chart.min.js"></script>
    <meta charset="UTF-8">
    <title>Person Details</title>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <link rel='stylesheet' href='https://fonts.googleapis.com/css?family=Roboto'>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
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
            <td >
                <select id="type" onchange="reload()">
                </select>
            </td>
        </tr>
    </table>
</div>
<div style="width:800px; height:800px">
    <canvas id="graph"/>
</div>
</body>
<script>

    var id = ${id};
    var displayName;

    $.get( "/elo-api/rest/core/getTypes",
            function( data ) {
                var select = $('#type');
                $('#type').append(new Option('All', 'ALL'));
                data.forEach(function(element) {
                    $('#type').append(new Option(element.displayName, element.name));
                });
            });

    $.get( '/elo-api/rest/report/getPersonName?id=' + id,
            function( data ) {
                displayName = data;
                reloadResults('ALL');
            }, 'text'
            );



    function reloadResults(type) {
        var url = '/elo-api/rest/report/getPersonDetails?id=' + id;
        if(type != 'ALL') {
            url += '&type=' + type;
        }
        $.get( url,
                function( data ) {
                    var rowData = [];
                    var labelData = [];
                    data.forEach(function(element) {
                        rowData.push(element.rating);
                        labelData.push(element.date);
                    });

                    var ctx = $("#graph");
                    var lineChart = new Chart(ctx, {
                        type: 'line',
                        data: {
                            datasets: [{
                                label: displayName,
                                fill: false,
                                data: rowData
                            }],
                            labels: labelData
                        },
                        options: {
                            scales: {
                                xAxes: [{
                                    ticks: {
                                        callback: function(value, index, values) {
                                            return value.substring(0, 10);
                                        },
                                        stepSize: 10
                                    }
                                }]
                            }
                        }
                    });
                });
    }

    function reload() {
        var type = $("#type").val();
        reloadResults(type);
    }


</script>
</html>