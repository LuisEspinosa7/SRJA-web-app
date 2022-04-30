(function(angular){
    'use strict';	
	angular.module('administrador',['ngMaterial','ngRoute','ngAnimate','menu.controller',
	                                'passwordConfirmation.directive',
	                                
	                                'listaEspacios.controller',
	                                'habitacion1.controller',
	                                'jardin.controller',
	                                'espacios.controller',
	                                'nodos.controller',
	                                'dispositivos.controller',
	                                'dispositivosItems.controller',
	                                
	                                'usuario.controller',
	                                'vivienda.controller'
	                                
	                                ]);
	
	
	angular.module('menu.controller',['menu.service', 'viviendaSocket.service', 'ngMaterial', 'ngMessages']);
	angular.module('menu.service',[]);
	angular.module('viviendaSocket.service',[]);
	
	
	angular.module('passwordConfirmation.directive',['ngMaterial', 'ngMessages']);	
	angular.module('listaEspacios.controller',['viviendaSocket.service', 'ngMaterial', 'ngResource', 'ngMessages']);	
	angular.module('habitacion1.controller',['viviendaSocket.service', 'ngMaterial', 'ngResource', 'ngMessages']);
	angular.module('jardin.controller',['ngMaterial', 'ngResource', 'ngMessages']);
	angular.module('espacios.controller',['ngMaterial', 'ngResource', 'ngMessages']);
	angular.module('nodos.controller',['ngMaterial', 'ngResource', 'ngMessages']);
	angular.module('dispositivos.controller',['ngMaterial', 'ngResource', 'ngMessages']);
	angular.module('dispositivosItems.controller',['ngMaterial', 'ngResource', 'ngMessages']);
	
	angular.module('usuario.controller',['ngMaterial', 'ngResource', 'ngMessages']);	
	angular.module('vivienda.controller',['ngMaterial', 'ngResource', 'ngMessages']);
	
	
})(window.angular);
