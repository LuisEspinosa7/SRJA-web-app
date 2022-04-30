(function() {
	angular.module('viviendaSocket.service')
		.service('ViviendaSocketService',
				ViviendaSocketService);
	
	function ViviendaSocketService($q, $timeout) {
		var service = {}
			, listenerConexion = $q.defer()
			, listenerDispositivosItemActuadores = $q.defer()
			, listenerEventos = $q.defer()
			, socket = {
				cliente: null, 
				stomp: null
			}, messageDispositivosItemIds = []
			, messageConexionIds = [];
		
		service.RECONNECT_TIMEOUT = 30000;
		service.SOCKET_URL = '/domotica/vivienda';
		
		service.DISPOSITIVOS_ITEM_ACTUADORES_TOPIC = '/topic/dispositivositem/actuadores';
		service.DISPOSITIVOS_ITEM_ACTUADORES_BROKER = '/app/dispositivositem/actuadores';		
		
		service.VIVIENDA_CONEXION_TOPIC = '/topic/viviendaconexion';
		service.VIVIENDA_CONEXION_BROKER = '/app/viviendaconexion';
		
		service.VIVIENDA_EVENTOS_TOPIC = '/topic/eventos';
		
		
		
		service.receiveConexionInfo = function() {
			return listenerConexion.promise;
		};
		
		
		service.receiveDispositivosItemActuadores = function() {
			return listenerDispositivosItemActuadores.promise;
		};
		
		
		service.receiveEventoInfo = function() {
			return listenerEventos.promise;
		};
		
		
		
		/**
		service.sendDispositivoItemActuador = function(dispositivoItem) {
			
			socket.stomp.send(service.DISPOSITIVOS_BROKER, 
					{}, 
					JSON.stringify({ 
						dispositivoItem : dispositivoItem 
					})
			);
			
		};
		
		
		service.send = function(message) {
			var id = Math.floor(Math.random() * 10000000);
			socket.stomp.send(service.VIVIENDA_CONEXION_BROKER, {
				priority: 9
			}, JSON.stringyfy({
				message: message,
				id: id
			}));
			messageConexionIds.push(id);
		};
		**/
		
			
		/**
		service.startListenerDispositivosItem = function() {
						
		};
		**/
		
		var reconnect = function() {
			$timeout(function(){
				initialize();
			}, this.RECONNECT_TIMEOUT);
		};
		
		var getMessage = function(data) {	
			return JSON.parse(data);
		}
		
		
		var startListeners = function() {
			
			socket.stomp.subscribe("/app/viviendaconexion", function(data){		
				console.log("Suscribiendo a BROKER VIVIENDA CONEXION");
				listenerConexion.notify(getMessage(data.body));
			})
			
			socket.stomp.subscribe(service.VIVIENDA_CONEXION_TOPIC, function(data){
				console.log("Suscribiendo a TOPIC VIVIENDA CONEXION");
				listenerConexion.notify(getMessage(data.body));
			})
			
			
			socket.stomp.subscribe(service.DISPOSITIVOS_ITEM_ACTUADORES_BROKER, function(data){		
				console.log("Suscribiendo a BROKER VIVIENDA DISPOSITIVOS ACTUADORES");
				listenerDispositivosItemActuadores.notify(getMessage(data.body));
			})
					
			socket.stomp.subscribe(service.DISPOSITIVOS_ITEM_ACTUADORES_TOPIC, function(data){
				console.log("Suscribiendo a TOPIC VIVIENDA DISPOSITIVOS ACTUADORES");
				listenerDispositivosItemActuadores.notify(getMessage(data.body));
			})
			
			
			socket.stomp.subscribe(service.VIVIENDA_EVENTOS_TOPIC, function(data){
				console.log("Suscribiendo a TOPIC VIVIENDA EVENTOS");
				listenerEventos.notify(getMessage(data.body));
			})			
			
		};
			
		
		var initialize = function(){
			socket.client = new SockJS(service.SOCKET_URL);
			socket.stomp = Stomp.over(socket.client);
			socket.stomp.connect({}, startListeners);
			socket.stomp.onclose = reconnect;
		}
		
		initialize();
		return service;
	}
})();










