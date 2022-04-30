angular.module('listaEspacios.controller')
		.controller(
				'ListaEspaciosController',
				function(ViviendaSocketService, $scope, $http, $mdDialog, $mdMedia, $resource, $filter, $interval) {			

					//var self = this;
					var aplicacionTitulo = "Gestión Espacios";
					
					$scope.colors = ['#A60D0D', '#593D08', '#BFAF8F', '#2345A6', '#AED3F2', '#A60D0D'];
					
					
					function listarEspacios($http, $scope) {	
						$http.get('api/espacios/lista').success(function(data) {
							$scope.JSONEspacios = data;
						}).error(function(data) {
							console.log('Error: ' + data);
						});
					}	
					
					
					listarEspacios($http, $scope);
							
					
										
					
					
}).config( function( $mdIconProvider ){
    $mdIconProvider.iconSet("avatar", 'app/assets/libs/material/docs/app/icons/avatar-icons.svg', 128);
});
