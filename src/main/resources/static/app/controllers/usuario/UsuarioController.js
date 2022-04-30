angular.module('usuario.controller')
		.controller(
				'UsuarioController',
				function($scope, $http, $mdDialog, $mdMedia, $resource, $filter, $interval) {					

					var table;
					var aplicacionTitulo = "Gestión Usuario";
					
					$scope.showHints = false;
					table = $('#tblUsuarios').DataTable({
						"processing" : true,
						"serverSide" : true,
						"ajax" : "api/usuarios",
						"columns" : [ {
							"data" : "id"
						}, {
							"data" : "identificacion"
						}, {
							"data" : "nombre",
						}, {
							"data" : "apellido"
						}, {
							"data" : "username"
						},{
							"data" : "email",
							"visible":false
						}, {
							"data" : "ciudad",
							"visible":false
						}, {
							"data" : "fechaNacimiento",
							"visible":false
						}, {
							"data" : "telefono",
							"visible":false
						}, {
							"data" : "direccion",
							"visible":false
						}, {
							"data" : "tipoIdentificacion",
							"visible":false
						}, {
							"data" : "estado",
							"visible":false
						}, {
							"data" : "genero",
							"visible":false
						}, {
							"data" : "password",
							"visible":false
						}, {
							"data" : "perfil",
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

					$('#tblUsuarios tbody').on('click', 'tr', function() {
						if ($(this).hasClass('selected')) {
							$(this).removeClass('selected');
						} else {
							table.$('tr.selected').removeClass('selected');
							$(this).addClass('selected');
						}
					});
					
					
					$scope.status = '  ';
					$scope.customFullscreen = $mdMedia('xs') || $mdMedia('sm');

					var url = 'api/usuarios';
					var usuarioResource = $resource(url, {
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
					
					function listarTiposIdentificacion($http, $scope) {	
						$http.get('api/tiposIdentificacion/').success(function(data) {
							$scope.JSONTiposIdentificacion = data;
						}).error(function(data) {
							console.log('Error: ' + data);
						});
					}
					
					function listarEstados($http, $scope) {	
						$http.get('api/estados/entidad?entidad=USUARIO').success(function(data) {
							$scope.JSONEstados = data;
						}).error(function(data) {
							console.log('Error: ' + data);
						});
					}
					
					function listarGeneros($http, $scope) {	
						$http.get('api/generos').success(function(data) {
							$scope.JSONGeneros = data;
						}).error(function(data) {
							console.log('Error: ' + data);
						});
					}
					
					function listarPaises($http, $scope) {		
						$http.get('api/paises').success(function(data) {
							$scope.JSONPaises = data;
						}).error(function(data) {
							console.log('Error: ' + data);
						});
					}
					
					function listarPerfiles($http, $scope) {		
						$http.get('api/perfiles').success(function(data) {
							$scope.JSONPerfiles = data;
						}).error(function(data) {
							console.log('Error: ' + data);
						});
					}
					
					function listarDepartamentos($http, $scope, pais) {
						//$scope.asignatura.codigoUaa = '';
						$http.get('api/departamentos?idPais=' + pais).success(
								function(data) {
									$scope.JSONDepartamentos = data;
								}).error(function(data) {
							console.log('Error: ' + data);
						});
					}
					
					function listarCiudades($http, $scope, departamento) {
						//$scope.asignatura.codigoUaa = '';
						$http.get('api/ciudades?idDepartamento=' + departamento).success(
								function(data) {
									$scope.JSONCiudades = data;
								}).error(function(data) {
							console.log('Error: ' + data);
						});
					}
											
					
					function eliminar(){
						usuarioResource.eliminar({},
								$scope.usuario,
								function(data) {
									$mdDialog.hide();
									mostrarRespuesta(data);
								}, function(response) {
									$mdDialog.hide();
									mostrarRespuesta(response.data);
									
								})
					}
					
					

					function DialogController($scope, $http, $mdDialog, accion, items, $interval, maxDate) {
						
						$scope.accion = accion;
						$scope.usuario = items;
						
						$scope.activated = false;
						$scope.determinateValue = 30;
						$scope.isProcessing = false;
						$scope.maxDate = maxDate;

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
							$scope.usuario = {
									"nombre":null,
									"apellido":null,
									"email":null,
									"tipoIdentificacion":{"id":null},
									"identificacion":null,
									"estado":{"id":null},
									"genero":{"id":null},
									"pais":{"id":null},
									"departamento":{"id":null},
									"ciudad":{"id":null},
									"telefono":null,
									"direccion":null,
									"username":null,
									"password":null,
									"perfil":{"id":null},
									"fechaNacimiento":null
										};
						} 
						
						listarTiposIdentificacion($http, $scope);
						listarEstados($http, $scope);
						listarGeneros($http, $scope);
						listarPaises($http, $scope);
						listarPerfiles($http, $scope);
						
						$scope.obtenerDepartamentos = function() {							
							listarDepartamentos($http, $scope,$scope.usuario.pais.id);
						}
						
						$scope.obtenerCiudades = function() {							
							listarCiudades($http, $scope,$scope.usuario.departamento.id);
						}		
						
						if ($scope.accion == 'Modificar') {										
							$scope.obtenerDepartamentos();
							$scope.obtenerCiudades();
						} 
																		
						$scope.hide = function() {
							$mdDialog.hide();
						};
						$scope.cancel = function() {
							
							if (accion == "Modificar") {
								$scope.usuario.fechaNacimiento = $filter('date')($scope.usuario.fechaNacimiento, "yyyy-MM-dd");
							}
							
							$mdDialog.cancel();
						};
						
						
						$scope.answer = function(answer) {
							
							if ($scope.accion == 'Adicionar') {
								//$scope.usuario.fechaNacimiento = $filter('date')($scope.usuario.fechaNacimiento, "yyyy-MM-dd");
								$scope.isProcessing = true;	
								$scope.activated = true;
								usuarioResource.adicionar({}, $scope.usuario,
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
								usuarioResource.modificar({},
										$scope.usuario,
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
						$scope.usuario = table.row('.selected').data();
						if ($scope.usuario == undefined) {
							data={
									mensaje: "Debe seleccionar un registro para realizar esta acción."
							}
							mostrarRespuesta(data);
							return false;
						}
					    var confirm = $mdDialog.confirm()
					          .title('Quieres eliminar este registro?')
					          .textContent('Una vez eliminado no se podra recuperar el registro.')
					          .ariaLabel('Usuario')
					          .targetEvent(ev)
					          .ok('Eliminar!')
					          .cancel('Cancelar');
					    $mdDialog.show(confirm).then(function() {
					    	eliminar();
					    }, function() {
					      
					    });
					  };

					$scope.editar = function(accion) {
						console.log("Dentro de editar usuario");
						var useFullScreen = ($mdMedia('sm') || $mdMedia('xs')) && $scope.customFullscreen;
						
						if (accion == "Adicionar") {
							console.log("Dentro de adicionar usuario");
							$scope.usuario = {};
						} else {
							if (accion != "Adicionar") {
								console.log("Antes de asignar los datos");
								$scope.usuario = table.row('.selected').data();
								
								
								if ($scope.usuario == undefined) {
									data={
											mensaje: "Debe seleccionar un registro para realizar esta acción.----"
									}
									mostrarRespuesta(data);
									return false;
								}
								if ($scope.usuario.fechaNacimiento != null) {
									console.log('Fecha Nacimiento:');
									console.log($scope.usuario.fechaNacimiento);
									var fechaNacimientoForma = $filter('date')($scope.usuario.fechaNacimiento, "M/d/yyyy");
									console.log('Fecha Nacimiento Modificada:');
									console.log(fechaNacimientoForma);
									$scope.usuario.fechaNacimiento = new Date(fechaNacimientoForma);
									console.log('Fecha Nacimiento Definitiva:');
									console.log($scope.usuario.fechaNacimiento);
								}
							}
						}
						if (accion == "Adicionar" || accion == 'Modificar') {
						$mdDialog.show({
							controller : DialogController,
							templateUrl : 'usuario/forma.usuario.jsp',
							parent : angular.element(document.body),
							fullscreen : useFullScreen,
							locals : {
								items : $scope.usuario,
								accion : accion,
								maxDate : new Date()
							}
						}).then(
								function(answer) {
									
								});
						}
					};
					
					
				});
