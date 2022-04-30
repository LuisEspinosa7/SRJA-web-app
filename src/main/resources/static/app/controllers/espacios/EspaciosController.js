angular.module('espacios.controller')
		.controller(
				'EspaciosController',
				function($scope, $http, $mdDialog, $mdMedia, $resource, $filter, $interval) {					

					var table;
					var aplicacionTitulo = "Gestión Espacios";
					
					$scope.showHints = false;
					table = $('#tblEspacios').DataTable({
						"processing" : true,
						"serverSide" : true,
						"ajax" : "api/espacios",
						"columns" : [ {
							"data" : "id"
						}, {
							"data" : "nombre",
						}, {
							"data" : "ancho"
						},{
							"data" : "largo"
						}, {
							"data" : "estado.nombre"
						}, {
							"data" : "tipoEspacio.nombre"
						}, {
							"data" : "vivienda.direccion"
						}, {
							"data" : "descripcion",
							"visible":false							
						}					
						],
						"order": [[ 0, "desc" ]],
						"oLanguage": {
		                    "sProcessing": "Procesando...",
		                    "sLengthMenu": "Mostrar _MENU_ registros",
		                    "sZeroRecords": "No se encontraron resultados",
		                    "sEmptyTable": "Ningún dato disponible en esta tabla",
		                    "sInfo": "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
		                    "sInfoEmpty": "Mostrando registros del 0 al 0 de un total de 0 registros",
		                    "sInfoFiltered": "(filtrado de un total de _MAX_ registros)",
		                    "sInfoPostFix": "",
		                    "sSearch": "Buscar:",
		                    "sUrl": "",
		                    "sInfoThousands": ",",
		                    "sLoadingRecords": "Cargando...",
		                    "oPaginate": {
		                        "sFirst": "Primero",
		                        "sLast": "Último",
		                        "sNext": "Siguiente",
		                        "sPrevious": "Anterior"}}
					});

					$('#tblEspacios tbody').on('click', 'tr', function() {
						if ($(this).hasClass('selected')) {
							$(this).removeClass('selected');
						} else {
							table.$('tr.selected').removeClass('selected');
							$(this).addClass('selected');
						}
					});
					
					
					$scope.status = '  ';
					$scope.customFullscreen = $mdMedia('xs') || $mdMedia('sm');

					var url = 'api/espacios';
					var espacioResource = $resource(url, {
						id : '@id'
					}, {
						adicionar : {
							url : url,
							method : 'POST',
							params : {}
						},
						modificar : {
							url : url + '/:id',
							method : 'PUT',
							params : {}
						},
						eliminar : {
							url : url + '/:id',
							method : 'DELETE',
							params : {}
						}
					});
					
					
					function listarEstados($http, $scope) {	
						$http.get('api/estados/entidad?entidad=ESPACIO').success(function(data) {
							$scope.JSONEstados = data;
						}).error(function(data) {
							console.log('Error: ' + data);
						});
					}	
					
					function listarTiposEspacio($http, $scope) {	
						$http.get('api/tiposEspacio/').success(function(data) {
							$scope.JSONTiposEspacio = data;
						}).error(function(data) {
							console.log('Error: ' + data);
						});
					}
					
					
					function listarViviendas($http, $scope) {	
						$http.get('api/viviendas/').success(function(datos) {
							console.log('Viviendas' + datos.data);
							$scope.JSONViviendas = datos.data;
						}).error(function(data) {
							console.log('Error: ' + data);
						});
					}
					
								
											
					
					function eliminar(){
						espacioResource.eliminar({},
								$scope.espacio,
								function(data) {
									$mdDialog.hide();
									mostrarRespuesta(data);
								}, function(response) {
									$mdDialog.hide();
									mostrarRespuesta(response.data);
									
								})
					}
					
					

					function DialogController($scope, $http, $mdDialog, accion, items, $interval) {
						
						$scope.accion = accion;
						$scope.espacio = items;
						
						$scope.activated = false;
						$scope.determinateValue = 30;
						$scope.isProcessing = false;
						

						// Iterate every 100ms, non-stop and increment
					      // the Determinate loader.
					      $interval(function() {
					    	  console.log('Girando ');
					    	  $scope.determinateValue += 1;
					        if ($scope.determinateValue > 100) {
					        	$scope.determinateValue = 30;
					        }

					      }, 100);
						
						
						if ($scope.accion == 'Adicionar') {
							
							$scope.espacio = {
									"nombre":null,
									"descripcion":null,
									"ancho":null,
									"largo":null,
									"vivienda":{"id":null},
									"estado":{"id":null},
									"tipoEspacio":{"id":null}									
							};
						} 
						
						listarTiposEspacio($http, $scope);
						listarEstados($http, $scope);
						listarViviendas($http, $scope);
																														
						$scope.hide = function() {
							$mdDialog.hide();
						};
						
						$scope.cancel = function() {						
							$mdDialog.cancel();
						};
												
						$scope.answer = function(answer) {
							
							if ($scope.accion == 'Adicionar') {
								$scope.isProcessing = true;	
								$scope.activated = true;
								espacioResource.adicionar({}, $scope.espacio,
										function(data) {
											$scope.isProcessing = false;											
											$scope.activated = false;
											$mdDialog.hide(answer);
											mostrarRespuesta(data);
										}, function(response) {
											$scope.isProcessing = false;	
											$scope.activated = false;
											$scope.MsgError = response.data.mensaje;
											$scope.showHints = true;
											mostrarRespuesta(response.data);
										})
							}
							
							if ($scope.accion == "Modificar") {
								$scope.isProcessing = true;	
								$scope.activated = true;
								espacioResource.modificar({},
										$scope.espacio,
										function(data) {
											$scope.isProcessing = false;	
											$scope.activated = false;
											$mdDialog.hide(answer);
											mostrarRespuesta(data);
										}, function(response) {
											$scope.isProcessing = false;	
											$scope.activated = false;
											$scope.MsgError = response.data.mensaje;
											$scope.showHints = true;
											mostrarRespuesta(response.data);
										})
							}
						};							
						
					}

					function mostrarRespuesta(data) {
						table.ajax.reload();
						$mdDialog.show($mdDialog.alert()
								.title(aplicacionTitulo).textContent(
										data.mensaje).ariaLabel(
										aplicacionTitulo).ok('Aceptar'));
					}
					
					$scope.dialogEliminar = function(ev) {
					    // Appending dialog to document.body to cover sidenav in docs app
						$scope.espacio = table.row('.selected').data();
						if ($scope.espacio == undefined) {
							data={
									mensaje: "Debe seleccionar un registro para realizar esta acción."
							}
							mostrarRespuesta(data);
							return false;
						}
					    var confirm = $mdDialog.confirm()
					          .title('Quieres eliminar este registro?')
					          .textContent('Una vez eliminado no se podra recuperar el registro.')
					          .ariaLabel('Espacio')
					          .targetEvent(ev)
					          .ok('Eliminar!')
					          .cancel('Cancelar');
					    $mdDialog.show(confirm).then(function() {
					    	eliminar();
					    }, function() {
					      
					    });
					  };

					$scope.editar = function(accion) {
						console.log("Dentro de editar espacio");
						var useFullScreen = ($mdMedia('sm') || $mdMedia('xs')) && $scope.customFullscreen;
						
						if (accion == "Adicionar") {
							console.log("Dentro de adicionar Espacio");
							$scope.espacio = {};
						} else {
							if (accion != "Adicionar") {
								console.log("Antes de asignar los datos");
								
								$scope.espacio = table.row('.selected').data();
								
								
								if ($scope.espacio == undefined) {
									data={
											mensaje: "Debe seleccionar un registro para realizar esta acción."
									}
									mostrarRespuesta(data);
									return false;
								}								
							}
						}
						if (accion == "Adicionar" || accion == 'Modificar') {
						$mdDialog.show({
							controller : DialogController,
							templateUrl : 'espacio/forma.espacio.jsp',
							parent : angular.element(document.body),
							fullscreen : useFullScreen,
							locals : {
								items : $scope.espacio,
								accion : accion
							}
						}).then(
								function(answer) {
									
								});
						}
					};
					
					
				});
