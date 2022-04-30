angular.module('dispositivos.controller')
		.controller(
				'DispositivosController',
				function($scope, $http, $mdDialog, $mdMedia, $resource, $filter, $interval) {					

					var table;
					var aplicacionTitulo = "Gestión Dispositivos";
					
					$scope.showHints = false;
					table = $('#tblDispositivos').DataTable({
						"processing" : true,
						"serverSide" : true,
						"ajax" : "api/dispositivos",
						"columns" : [ {
							"data" : "id"
						}, {
							"data" : "nombre",
						}, {
							"data" : "estado.nombre"
						}, {
							"data" : "tipoDispositivo.nombre"
						}, {
							"data" : "categoria.nombre"						
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

					$('#tblDispositivos tbody').on('click', 'tr', function() {
						if ($(this).hasClass('selected')) {
							$(this).removeClass('selected');
						} else {
							table.$('tr.selected').removeClass('selected');
							$(this).addClass('selected');
						}
					});
					
					
					$scope.status = '  ';
					$scope.customFullscreen = $mdMedia('xs') || $mdMedia('sm');

					var url = 'api/dispositivos';
					var dispositivoResource = $resource(url, {
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
						$http.get('api/estados/entidad?entidad=DISPOSITIVO').success(function(data) {
							$scope.JSONEstados = data;
						}).error(function(data) {
							console.log('Error: ' + data);
						});
					}	
					
					function listarTiposDispositivo($http, $scope) {	
						$http.get('api/tiposDispositivo/').success(function(data) {
							$scope.JSONTiposDispositivo = data;
						}).error(function(data) {
							console.log('Error: ' + data);
						});
					}
					
					
					function listarCategorias($http, $scope) {	
						$http.get('api/categoria/').success(function(data) {
							$scope.JSONCategorias = data;
						}).error(function(data) {
							console.log('Error: ' + data);
						});
					}	
											
					
					function eliminar(){
						dispositivoResource.eliminar({},
								$scope.dispositivo,
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
						$scope.dispositivo = items;
						
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
							
							$scope.dispositivo = {
									"codigo":null,							
									"nombre":null,									
									"estado":{"id":null},
									"tipoDispositivo":{"id":null},
									"espacio":{"id":null}	
							};
						} 
						
						listarEstados($http, $scope);
						listarTiposDispositivo($http, $scope);						
						listarCategorias($http, $scope);
						
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
								dispositivoResource.adicionar({}, $scope.dispositivo,
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
								dispositivoResource.modificar({},
										$scope.dispositivo,
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
						if ($scope.dispositivo == undefined) {
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
							console.log("Dentro de adicionar Dispositivo");
							$scope.dispositivo = {};
						} else {
							if (accion != "Adicionar") {
								console.log("Antes de asignar los datos");
								
								$scope.dispositivo = table.row('.selected').data();
								
								
								if ($scope.dispositivo == undefined) {
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
							templateUrl : 'dispositivo/forma.dispositivo.jsp',
							parent : angular.element(document.body),
							fullscreen : useFullScreen,
							locals : {
								items : $scope.dispositivo,
								accion : accion
							}
						}).then(
								function(answer) {
									
								});
						}
					};
					
					
				});
