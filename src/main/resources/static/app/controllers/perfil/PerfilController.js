angular.module('perfil.controller').controller('PerfilController',
		function($scope, $http, $mdDialog, $mdMedia, $resource) {

			$http.get('perfilSer').success(function(data) {
				$scope.persona = {
					"apellido" : data.usuario.persona.apellido,
					"nombre" : data.usuario.persona.nombre,
					"tipoIdentificacion" : {
						"nombre" : data.usuario.persona.tipoIdentificacion.nombre,
						"codigo" : data.usuario.persona.tipoIdentificacion.codigo
					},
					"identificacion" : data.usuario.persona.identificacion,
					"email" : data.usuario.persona.email,
					"telefono" : data.usuario.persona.telefono,
					"direccionResidencia" : data.usuario.persona.direccionResidencia,
					"rol":data.grupo.nombre
				}
				$scope.JSONNbc = data;
			}).error(function(data) {
				console.log('Error: ' + data);
			});

		})
