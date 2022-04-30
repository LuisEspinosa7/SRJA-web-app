angular.module('jardin.controller')
		.controller(
				'JardinController',
				function(ViviendaSocketService,$scope, $http, $mdDialog, $mdMedia, $resource, $filter, $interval, $mdMenu) {					

					var table;
					var aplicacionTitulo = "Jardin";
					
					var chart1; // global
					var chart2; // global
					var chartSoilHumidity1;
					var chartSoilHumidity2;
					var chartFlowSpeed;
					var chartRain;
					
					var chartSoilTemperature;
					
					
					$scope.actuadoresLista = [];									
					$scope.showHints = false;	
					
					var originatorEv;
					
					$scope.status = '  ';
					$scope.customFullscreen = $mdMedia('xs') || $mdMedia('sm');
									
					$scope.colors = ['#A60D0D', '#593D08', '#BFAF8F', '#2345A6', '#AED3F2', '#A60D0D'];
					
					
					function requestData1() {
					    $.ajax({
					        //url: 'http://localhost/live-server/live-server-data.php',
					        url: 'http://localhost:8080/domotica/api/dispositivosItem/liveData/1/3',
					        //url: 'http://localhost:8080/domotica/api/dispositivosItem/liveData/1/3',
					        success: function(json) {
					        	// [1506787465000,38]
					            var series = chart1.series[0],
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
									setTimeout(requestData1, 6000);
								},
								cache : false
							});
					}
					
					
					function requestData2() {
					    $.ajax({
					        //url: 'http://localhost/live-server/live-server-data.php',
					        url: 'http://localhost:8080/domotica/api/dispositivosItem/liveData/1/1',
					        //url: 'http://localhost:8080/domotica/api/dispositivosItem/liveData/1/3',
					        success: function(json) {
					        	// [1506787465000,38]
					            var series = chart2.series[0],
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
									setTimeout(requestData2, 6000);
								},
								cache : false
							});
					}
					
					
					function requestDataSoilTemperature() {
					    $.ajax({
					        
					        url: 'http://localhost:8080/domotica/api/dispositivosItem/liveData/5/11',
					        success: function(json) {
					        	// [1506787465000,38]
					            var series = chartSoilTemperature.series[0],
					                shift = series.data.length > 20; // shift if the series is 
					                                                 // longer than 20
								
					                
					                                                 
									var time = (new Date()).getTime();
									console.log("hora: " + time);
					                
									series.addPoint([time, json.data], true, shift);
									
									// call it again after one second
									setTimeout(requestDataSoilTemperature, 6000);
								},
								cache : false
							});
					}
					
					
					function plotTemperature() {
						Highcharts.setOptions({
							global : {
								useUTC : false
							}
						});

						chart1 = new Highcharts.chart({
							chart : {
								renderTo: 'container1',
								type : 'spline',
								animation : Highcharts.svg, // don't animate in old IE
								marginRight : 10,
								events : {
									load : requestData1
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
									text : '°C'
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
							} ],
							
							responsive: {
						        rules: [{
						            condition: {
						                maxWidth: 500
						            },
						            chartOptions: {
						                legend: {
						                    align: 'center',
						                    verticalAlign: 'bottom',
						                    layout: 'horizontal'
						                },
						                yAxis: {
						                    labels: {
						                        align: 'left',
						                        x: 0,
						                        y: -5
						                    },
						                    title: {
						                        text: null
						                    }
						                },
						                subtitle: {
						                    text: null
						                },
						                credits: {
						                    enabled: false
						                }
						            }
						        }]
						    }
							
							
						});
					}
					
					
					
					function plotHumidity() {
						Highcharts.setOptions({
							global : {
								useUTC : false
							}
						});

						chart2 = new Highcharts.chart({
							chart : {
								renderTo: 'container2',
								type : 'spline',
								animation : Highcharts.svg, // don't animate in old IE
								marginRight : 10,
								events : {
									load : requestData2
								}
							},
							title : {
								text : 'Humedad de Jardin'
							},
							xAxis : {
								type : 'datetime',
								tickPixelInterval : 150
							},
							yAxis : {
								title : {
									text : '% Humedad'
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
								name : 'Humedad de Jardin',
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
							} ],
							
							responsive: {
						        rules: [{
						            condition: {
						                maxWidth: 500
						            },
						            chartOptions: {
						                legend: {
						                    align: 'center',
						                    verticalAlign: 'bottom',
						                    layout: 'horizontal'
						                },
						                yAxis: {
						                    labels: {
						                        align: 'left',
						                        x: 0,
						                        y: -5
						                    },
						                    title: {
						                        text: null
						                    }
						                },
						                subtitle: {
						                    text: null
						                },
						                credits: {
						                    enabled: false
						                }
						            }
						        }]
						    }
							
							
						});
					}
					
					
					
					function plotSoilTemperature() {
						Highcharts.setOptions({
							global : {
								useUTC : false
							}
						});

						chartSoilTemperature = new Highcharts.chart({
							chart : {
								renderTo: 'containerSoilTemperature',
								type : 'spline',
								animation : Highcharts.svg, // don't animate in old IE
								marginRight : 10,
								events : {
									load : requestDataSoilTemperature
								}
							},
							title : {
								text : 'Temperatura de Tierra'
							},
							xAxis : {
								type : 'datetime',
								tickPixelInterval : 150
							},
							yAxis : {
								title : {
									text : '°C'
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
								name : 'Temperatura de Tierra',
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
							} ],
							
							responsive: {
						        rules: [{
						            condition: {
						                maxWidth: 500
						            },
						            chartOptions: {
						                legend: {
						                    align: 'center',
						                    verticalAlign: 'bottom',
						                    layout: 'horizontal'
						                },
						                yAxis: {
						                    labels: {
						                        align: 'left',
						                        x: 0,
						                        y: -5
						                    },
						                    title: {
						                        text: null
						                    }
						                },
						                subtitle: {
						                    text: null
						                },
						                credits: {
						                    enabled: false
						                }
						            }
						        }]
						    }
							
							
						});
					}
					
					
					
					plotTemperature();				
					plotHumidity();
					plotSoilTemperature();
					
					
					
					
					var gaugeOptions = {

						    chart: {
						        type: 'solidgauge'
						    },

						    title: null,

						    pane: {
						        center: ['50%', '85%'],
						        size: '140%',
						        startAngle: -90,
						        endAngle: 90,
						        background: {
						            backgroundColor: (Highcharts.theme && Highcharts.theme.background2) || '#EEE',
						            innerRadius: '60%',
						            outerRadius: '100%',
						            shape: 'arc'
						        }
						    },

						    tooltip: {
						        enabled: false
						    },

						    // the value axis
						    yAxis: {
						        stops: [
						        	[0.1, '#DF5353'], // red
						            [0.5, '#55BF3B'], // green
						            [0.9, '#2345A6'] // blue
						        ],
						        lineWidth: 0,
						        minorTickInterval: null,
						        tickAmount: 2,
						        title: {
						            y: -70
						        },
						        labels: {
						            y: 16
						        }
						    },

						    plotOptions: {
						        solidgauge: {
						            dataLabels: {
						                y: 5,
						                borderWidth: 0,
						                useHTML: true
						            }
						        }
						    }
						};
					
					
					
					
					
					function requestDataSH1() {
					    $.ajax({
					        
					        url: 'http://localhost:8080/domotica/api/dispositivosItem/liveData/6/10',
					        success: function(json) {
					        	
					        	var point, tnewVal, newVal;
					        	point = chartSoilHumidity1.series[0].points[0];
					            tnewVal = json.data;
					            newVal = parseInt((tnewVal * 100) / 1023);
					            point.update(newVal);    
					            
									setTimeout(requestDataSH1, 6000);
								},
								cache : false
							});
					}
					
					
					// The soil humidity 1
					chartSoilHumidity1 = Highcharts.chart('container-sh1a', Highcharts.merge(gaugeOptions, {
					    yAxis: {
					        min: 0,
					        max: 100,
					        title: {
					            text: 'Humedad Tierra 1'
					        }
					    },

					    credits: {
					        enabled: false
					    },

					    series: [{
					        name: 'Humedad Tierra 1',
					        data: [0],
					        dataLabels: {
					            format: '<div style="text-align:center"><span style="font-size:25px;color:' +
					                ((Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black') + '">{y}</span><br/>' +
					                   '<span style="font-size:12px;color:silver">% Humedad</span></div>'
					        },
					        tooltip: {
					            valueSuffix: ' % Humedad'
					        }
					    }]				    
					    

					}));
					
					requestDataSH1();
					
					
					
					
					
					function requestDataSH2() {
					    $.ajax({
					        
					        url: 'http://localhost:8080/domotica/api/dispositivosItem/liveData/7/10',
					        success: function(json) {
					        	
					        	var point, tnewVal, newVal;
					        	point = chartSoilHumidity2.series[0].points[0];
					        	tnewVal = json.data;
					            newVal = parseInt((tnewVal * 100) / 1023);
					            point.update(newVal);    
					            
									setTimeout(requestDataSH2, 6000);
								},
								cache : false
							});
					}
					
					// The soil humidity 2
					chartSoilHumidity2 = Highcharts.chart('container-sh1b', Highcharts.merge(gaugeOptions, {
					    yAxis: {
					        min: 0,
					        max: 100,
					        title: {
					            text: 'Humedad Tierra 2'
					        }
					    },

					    credits: {
					        enabled: false
					    },

					    series: [{
					        name: 'Humedad Tierra 2',
					        data: [0],
					        dataLabels: {
					            format: '<div style="text-align:center"><span style="font-size:25px;color:' +
					                ((Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black') + '">{y}</span><br/>' +
					                   '<span style="font-size:12px;color:silver">% Humedad</span></div>'
					        },
					        tooltip: {
					            valueSuffix: ' % Humedad'
					        }
					    }]

					}));
					
										
					requestDataSH2();
					
					
					function requestDataFlowSpeed() {
					    $.ajax({
					        
					        url: 'http://localhost:8080/domotica/api/dispositivosItem/liveData/9/9',
					        success: function(json) {
					        	
					        	var point, tnewVal, newVal;
					        	point = chartFlowSpeed.series[0].points[0];
					        	tnewVal = json.data;
					            newVal = parseInt((tnewVal * 100) / 1023);
					            point.update(newVal);    
					            
									setTimeout(requestDataFlowSpeed, 6000);
								},
								cache : false
							});
					}
					
					// The soil humidity 2
					chartFlowSpeed = Highcharts.chart('container-flow', Highcharts.merge(gaugeOptions, {
					    yAxis: {
					        min: 0,
					        max: 100,
					        title: {
					            text: 'Velocidad de Flujo'
					        }
					    },

					    credits: {
					        enabled: false
					    },

					    series: [{
					        name: 'Velocidad de Flujo',
					        data: [0],
					        dataLabels: {
					            format: '<div style="text-align:center"><span style="font-size:25px;color:' +
					                ((Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black') + '">{y}</span><br/>' +
					                   '<span style="font-size:12px;color:silver">L/M</span></div>'
					        },
					        tooltip: {
					            valueSuffix: ' L/M'
					        }
					    }]

					}));				
					
					requestDataFlowSpeed();
					
					
					
					var gaugeOptions2 = {

						    chart: {
						        type: 'solidgauge'
						    },

						    title: null,

						    pane: {
						        center: ['50%', '85%'],
						        size: '140%',
						        startAngle: -90,
						        endAngle: 90,
						        background: {
						            backgroundColor: (Highcharts.theme && Highcharts.theme.background2) || '#EEE',
						            innerRadius: '60%',
						            outerRadius: '100%',
						            shape: 'arc'
						        }
						    },

						    tooltip: {
						        enabled: false
						    },

						    // the value axis
						    yAxis: {
						        stops: [
						        	[0.9, '#D2CC55'], // amarillo
						        	[1, '#2345A6'] // azul
						            
						        ],
						        lineWidth: 0,
						        minorTickInterval: null,
						        tickAmount: 2,
						        title: {
						            y: -70
						        },
						        labels: {
						            y: 16
						        }
						    },

						    plotOptions: {
						        solidgauge: {
						            dataLabels: {
						                y: 5,
						                borderWidth: 0,
						                useHTML: true
						            }
						        }
						    }
						};
					
					
					
					function requestDataRain() {
					    $.ajax({
					        
					        url: 'http://localhost:8080/domotica/api/dispositivosItem/liveData/8/8',
					        success: function(json) {
					        	
					        	var point, tnewVal, newVal;
					        	point = chartRain.series[0].points[0];
					        	newVal = json.data;					           
					            point.update(newVal);    
					            
									setTimeout(requestDataRain, 6000);
								},
								cache : false
							});
					}
					
					// rain
					chartRain = Highcharts.chart('container-rain', Highcharts.merge(gaugeOptions2, {
					    yAxis: {
					        min: 0,
					        max: 1,
					        title: {
					            text: 'Lluvia'
					        }
					    },

					    credits: {
					        enabled: false
					    },

					    series: [{
					        name: 'Lluvia',
					        data: [0],
					        dataLabels: {
					            format: '<div style="text-align:center"><span style="font-size:25px;color:' +
					                ((Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black') + '">{y}</span><br/>' +
					                   '<span style="font-size:12px;color:silver">Lluvia</span></div>'
					        },
					        tooltip: {
					            valueSuffix: ' Lluvia'
					        }
					    }]

					}));				
					
					requestDataRain();
					
					
					
					
					
					
					
					
					
					
					function setDivHeight() {
					    var div = $('#container-sh1a');
					    div.height(div.width() * 0.75);
					    div = $('#container-sh1b');
					    div.height(div.width() * 0.75);
					}

					$(window).on('load resize', function(){
					    setDivHeight();        
					});
					
					
					
					
					function listarDispositivosItemActuadores($http, $scope) {	
						$http.get('api/dispositivosItem/actuadores/9').success(function(data) {
							$scope.actuadoresLista = data;
						}).error(function(data) {
							console.log('Error: ' + data);
						});
					}	
					
					
					listarDispositivosItemActuadores($http, $scope);
					
					
					
					
					$scope.dispositivoItemActuadorEncendido = function(dispositivoItemActuador) {
						console.log('Obteniendo valor encendido del dispositivo');
						
						for(var i = 0; i < dispositivoItemActuador.valores.length; i++){
							console.log("Dentro del FOR");
							
							if (dispositivoItemActuador.valores[i].tipoValor.nombre === 'ENCENDIDO') {
								console.log("OBTENIENDO ENCENDIDO");
								
								if (dispositivoItemActuador.valores[i].valor === 1) {
									return true;
								}
														
							}
														
						}
						
						return false;						
					}
																			
					
					 $scope.openMenu = function($mdOpenMenu, ev) {
					      originatorEv = ev;
					      $mdOpenMenu(ev);
					 };
					
					 
					 $scope.changeRGB = function(dispositivoItemActuador, ev) {
						 $mdDialog.show({
						      controller: RGBDialogController,
						      templateUrl: 'habitacion1/forma.rgb.jsp',
						      parent: angular.element(document.body),
						      targetEvent: ev,
						      clickOutsideToClose:false,
						      fullscreen: $scope.customFullscreen,// Only for -xs, -sm breakpoints.
						      locals : {
								items : dispositivoItemActuador
						      }					 
						 
						 	})
						    .then(function(answer) {
						    	//alert('Color Cambiado');
						      //$scope.status = 'You said the information was "' + answer + '".';
						    }, function() {
						    	//alert('Color NO Cambiado');
						    	//$scope.status = 'You cancelled the dialog.';
						    });

					      originatorEv = null;
					    };
					 
					
					 $scope.toggledispositivoItemActuadorEncendido = function(dispositivoItemActuador) {
						 console.log('Cambiando estado de encendido.....');
						 
						 for(var i = 0; i < dispositivoItemActuador.valores.length; i++){
								console.log("Dentro del FOR para cambiar estado encendido :)");
								
								if (dispositivoItemActuador.valores[i].tipoValor.nombre === 'ENCENDIDO') {
									console.log(" si existe el valor llamado ENCENDIDO");
									
									if (dispositivoItemActuador.valores[i].valor === 1) {
										console.log('estaba prendido - apagando....');
										dispositivoItemActuador.valores[i].valor = 0;
										
										dispositivosItemActuadoresResource.modificar({},
												dispositivoItemActuador,
												function(data) {
													$mdDialog.hide();
													mostrarRespuesta(data);
												}, function(response) {
													mostrarRespuesta(response.data);
										})
										
										
									}else{
										console.log('estaba apagado - prediendo....');
										dispositivoItemActuador.valores[i].valor = 1
										
										dispositivosItemActuadoresResource.modificar({},
												dispositivoItemActuador,
												function(data) {
													$mdDialog.hide();
													mostrarRespuesta(data);
												}, function(response) {
													mostrarRespuesta(response.data);
										})
										
									}
															
								}
															
							}
						 
					 };
					 
					 ViviendaSocketService.receiveDispositivosItemActuadores().then(null, null, function(p){
														
						 	console.log("LLEGADA = " + p.body.valores[0].valor);
						 	
						 	var length = $scope.actuadoresLista.length;
						 	console.log("TAMAÑO = " + length);
						 	
						 	
						 	for (i = 0; i < length; i++) {
						 		console.log('Iterando en recepcion de informacion n ... ' + i);
						 		console.log($scope.actuadoresLista[i].id);	
						 		
						 		if ($scope.actuadoresLista[i].id == p.body.id) {
									console.log('Cambiando Valor: ' + i);
									$scope.actuadoresLista[i] = p.body;
								}
						 		
						 	};
							
						 	
						 	
						 	
						 	/**
						 	for(var i = 0; i < $scope.actuadoresLista.length; i++) {
						 		
						 		console.log('Iterando en recepcion de informacion n ... ' + i);
						 		
								if ($scope.actuadoresLista[i].id == p.body.id) {
									console.log('Cambiando Valor: ' + i);
									$scope.actuadoresLista[i] == p;
								}
								
							}
							**/
						 	//$scope.actuadoresLista.push(p);
						 
						})
						
						
					var url = 'api/dispositivosItem/actuadores';
					var dispositivosItemActuadoresResource = $resource(url, {
						id : '@id'
					}, {						
						modificar : {
							url : url + '/:id',
							method : 'PUT',
							params : {}
						}
					});
						
						
					
					function RGBDialogController($scope, $http, $mdDialog, items, $interval) {
						 
						 $scope.dispositivoItemActuador = items;
						 				 
						 
						$scope.RGBcolor = {
							red: 0,
							green: 0,
							blue: 0
						};
						 
						 
						function getColors(dispositivoItemActuador){
							
							console.log("Dentro del metodo de seleccionar colores");
							
													
							for(var i = 0; i < dispositivoItemActuador.valores.length; i++){
								console.log("Dentro del FOR");
								
								if (dispositivoItemActuador.valores[i].tipoValor.nombre === 'COLOR R') {
									console.log("Seleccionando ROJO");
									$scope.RGBcolor.red = dispositivoItemActuador.valores[i].valor;
						        }
								
								if (dispositivoItemActuador.valores[i].tipoValor.nombre === 'COLOR G') {
									console.log("Seleccionando VERDE");
									$scope.RGBcolor.green = dispositivoItemActuador.valores[i].valor;
						        }
								
								if (dispositivoItemActuador.valores[i].tipoValor.nombre === 'COLOR B') {
									console.log("Seleccionando AZUL");
									$scope.RGBcolor.blue = dispositivoItemActuador.valores[i].valor;
						        }
															
							}
							
						}
						 
						getColors($scope.dispositivoItemActuador);
											 
						 
						$scope.hide = function() {
							$mdDialog.hide();
						};
						 
						$scope.cancel = function() {
							$mdDialog.cancel();
						};
						 
						 
						 $scope.answer = function(answer) {
																
								if (answer == "Modificar") {
									
									console.log("Asignando nuevos valores a los colores....");							
									
									for(var i = 0; i < $scope.dispositivoItemActuador.valores.length; i++){
										console.log("Dentro del FOR");
										
										if ($scope.dispositivoItemActuador.valores[i].tipoValor.nombre === 'COLOR R') {
											console.log("Seleccionando ROJO");
											$scope.dispositivoItemActuador.valores[i].valor = $scope.RGBcolor.red;
								        }
										
										if ($scope.dispositivoItemActuador.valores[i].tipoValor.nombre === 'COLOR G') {
											console.log("Seleccionando VERDE");
											$scope.dispositivoItemActuador.valores[i].valor = $scope.RGBcolor.green;
								        }
										
										if ($scope.dispositivoItemActuador.valores[i].tipoValor.nombre === 'COLOR B') {
											console.log("Seleccionando AZUL");
											$scope.dispositivoItemActuador.valores[i].valor = $scope.RGBcolor.blue;
								        }
																	
									}
																																				
									dispositivosItemActuadoresResource.modificar({},
											$scope.dispositivoItemActuador,
											function(data) {
												$mdDialog.hide(answer);
												mostrarRespuesta(data);
											}, function(response) {
												mostrarRespuesta(response.data);
									})
								}
							};
						 
					 }
						
					
					
					
					function mostrarRespuesta(data) {
						
						$mdDialog.show($mdDialog.alert()
								.title(aplicacionTitulo).textContent(
										data.mensaje).ariaLabel(
										aplicacionTitulo).ok('Aceptar'));
					}
						
						
					
					
					
				}).config(function($mdThemingProvider) {
					  $mdThemingProvider.theme('dark-grey').backgroundPalette('grey').dark();
					  $mdThemingProvider.theme('dark-orange').backgroundPalette('orange').dark();
					  $mdThemingProvider.theme('dark-purple').backgroundPalette('deep-purple').dark();
					  $mdThemingProvider.theme('dark-blue').backgroundPalette('blue').dark();
					});
