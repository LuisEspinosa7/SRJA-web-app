<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Temperatura Jardin</title>
<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
<script src="https://code.highcharts.com/highcharts.js"></script>
<script src="https://code.highcharts.com/modules/exporting.js"></script>

<script>

var chart; // global

function requestData() {
    $.ajax({
        //url: 'http://localhost/live-server/live-server-data.php',
        url: 'http://localhost:8080/domotica/api/dispositivosItem/liveData/1/3',
        //url: 'http://localhost:8080/domotica/api/dispositivosItem/liveData/1/3',
        success: function(json) {
        	// [1506787465000,38]
            var series = chart.series[0],
                shift = series.data.length > 20; // shift if the series is 
                                                 // longer than 20
			
                
                                                 
				var time = (new Date()).getTime();
				console.log("hora: " + time);
                
                
				//fecha = time * 1000;
				// add the point
				//chart.series[0].addPoint(point, true, shift);
				//chart.series[0].addPoint([ fecha, json.data ], true, shift);
				series.addPoint([time, json.data], true, shift);
				
				// call it again after one second
				setTimeout(requestData, 6000);
			},
			cache : false
		});
	}

	$(document).ready(
			function() {
				Highcharts.setOptions({
					global : {
						useUTC : false
					}
				});

				chart = new Highcharts.chart('container', {
					chart : {
						type : 'spline',
						animation : Highcharts.svg, // don't animate in old IE
						marginRight : 10,
						events : {
							load : requestData
						}
					},
					title : {
						text : 'Temperatura de Jardin'
					},
					xAxis : {
						type : 'datetime',
						tickPixelInterval : 150
					},
					yAxis : {
						title : {
							text : 'Value'
						},
						plotLines : [ {
							value : 0,
							width : 1,
							color : '#808080'
						} ]
					},
					tooltip : {
						formatter : function() {
							return '<b>'
									+ this.series.name
									+ '</b><br/>'
									+ Highcharts.dateFormat(
											'%Y-%m-%d %H:%M:%S', this.x)
									+ '<br/>'
									+ Highcharts.numberFormat(this.y, 2);
						}
					},
					legend : {
						enabled : false
					},
					exporting : {
						enabled : false
					},
					series : [ {
						name : 'Temperatura de Jardin',
						data : (function() {
							// generate an array of random data
							var data = [], time = (new Date()).getTime(), i;

							for (i = -3; i <= 0; i += 1) {
								data.push({
									x : time + i * 1000,
									y : 0 + i
								});
							}
							return data;
						}())
					} ]
				});
			});
</script>

</head>
<body>


<div id="container" style="width: 800px; height: 400px; margin: 0 auto"></div>


</body>
</html>