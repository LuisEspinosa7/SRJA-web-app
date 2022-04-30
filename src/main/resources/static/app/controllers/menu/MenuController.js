(function(){
	'use strict';

	angular
		.module('menu.controller')
		.controller('MenuController',[
			'menuService',
			'ViviendaSocketService',
			'$mdSidenav',
			'$mdBottomSheet', 
			'$timeout',
			'$log',
			'$mdDialog',
			'$scope',
			'$resource',
			function (menuService, ViviendaSocketService, $mdSidenav, $mdBottomSheet, $timeout, $log, $mdDialog, $scope, $resource, $window){
				var self = this;

				self.selected = null;
				self.menu = [];
				self.conexionState = 'Connection Lost';
				self.connecting = true;
				self.selectMenu = selectMenu;
				self.toggleMenu   = toggleMenuList;
				self.cargarCalendar = cargarCalendar;
				self.salir = salir;
				self.portal = portal;
				
				self.fabIsOpen = false;
				self.fabSelectedMode = 'md-scale';
				self.fabSelectedDirection = 'left';
			
				
				
				
				var url = 'api/eventos';
				var eventosResource = $resource(url, {
					id : '@id'
				}, {						
					modificar : {
						url : url + '/:id',
						method : 'PUT',
						params : {}
					}
				});
				
				
				
				
				
				
				ion.sound({
				    sounds: [
				        {
				            alias: "s1",
				            name: "bell_ring"
				        },
				        {
				            alias: "s2",
				            name: "glass"
				        },
				        {
				            alias: "s3",
				            name: "water_droplet_2",
				            loop: 2
				        },
				        {
				            alias: "s4",
				            name: "light_bulb_breaking",
				            loop: true
				        }
				    ],

				    path: "app/assets/libs/ion.sound-3.0.7/sounds/",
				    preload: false,
				    volume: 1
				});	
				
				
				
				
				ViviendaSocketService.receiveConexionInfo().then(null, null, function(p){										
										
					self.conexionState = p.body.message;					
									
					if (self.conexionState !== "Connection Alive") {
						console.log("---- CONNECTION LOST -----");
						self.connecting = true;
						
					}else{
						console.log("---- CONNECTION ALIVE -----");						
						self.connecting = false;						
					}
						
					
				})	
				
				
				
							
				function mostrarEvento(data) {	
					
					 var confirm = $mdDialog.confirm()
	                  .title(data.tipoEvento.nombre)
	                  .textContent(data.mensaje)
	                  .ariaLabel('Toma una desicion!')
	                  .targetEvent(event)
	                  .ok('Entendido')
	               $mdDialog.show(confirm).then(function() {
	                  //$scope.status = 'Record deleted successfully!';
	            	   console.log('Evento Leido!!!');
	            	   
	            	   if (data.tipoEvento.nombre == "NOTIFICACION") {
							ion.sound.stop("s2");
						}else if(data.tipoEvento.nombre == "ALERTA"){
							ion.sound.stop("s1");
						}else if(data.tipoEvento.nombre == "RECORDATORIO"){
							ion.sound.stop("s3");
						}
	            	   
	            	   var evento = data;
	            	   evento.confirmado = 1;
	            	   
	            	   // Enviamos el la respuesta que el evento fue leido!
	            	   eventosResource.modificar({},
								evento,
								function(dta) {
									$mdDialog.hide();
									
								}, function(response) {
									$mdDialog.hide();
								})
	            	   
	            	   
	            	   
	            	   
	               }, function() {
	                  //$scope.status = 'You decided to keep your record.';
	            	   console.log('No leiste!!!');
	            	   
	               });
					
				}
				
				
				
				
				
				ViviendaSocketService.receiveEventoInfo().then(null, null, function(p){										
										
					//self.conexionState = p.body.message;	
					//document.getElementById("audio").play();	
					console.log('Tipo de Evento: ' + p.body.tipoEvento.nombre);
					
					if (p.body.tipoEvento.nombre == "NOTIFICACION") {
						ion.sound.play("s2", {
						    loop: 4,
						    volume: 1.0
						});
					}else if(p.body.tipoEvento.nombre == "ALERTA"){
						ion.sound.play("s1", {
						    loop: true,
						    volume: 1.0
						});
					}else if(p.body.tipoEvento.nombre == "RECORDATORIO"){
						ion.sound.play("s3", {
						    //loop: true,
						    volume: 1.0
						});
					}
					
					
					console.log("---- LLEGO UN EVENTO -----");
					console.log(p.body.mensaje);
					mostrarEvento(p.body);
				})		
				
				
				
													
				
				menuService
					.loadAllMenu()
					.then( function(menu){
						self.menu = [].concat(menu);
					});


				function toggleMenuList() {
			      $mdSidenav('left').toggle();
			    }
				
				function cargarCalendar(){
					setTimeout(function(){ $('#btnCalendar').click(); }, 200);
				}

			    function selectMenu ( link ) {
			      self.selected = angular.isNumber(link) ? $scope.menu[link] : link;
			    }
			    
			    function salir(){
			    	window.open('app.salir','_top');
			    }

			    function portal(){
			    	window.open('http://localhost:8080/domotica/logout','_self');
			    }
			    
			    function toggleSubMenuList( thing ) {
			      	if(thing){
			      		thing=false;
				    }else{
				    	thing=true;
				    }
				    return thing;
				}

			}]);

})();