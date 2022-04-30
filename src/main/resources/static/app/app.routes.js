(function(angular){
    'use strict';
	angular
		.module('administrador')
      	.config(function($mdThemingProvider, $mdIconProvider, $routeProvider, $locationProvider){
        	$routeProvider
        	$routeProvider.when('/', {
				templateUrl : 'view/home.jsp',
				controller : ''
			})
			
			//Administrador
			.when('/usuario', {
				templateUrl : 'usuario',
				controller : ''
			})
			.when('/vivienda', {
				templateUrl : 'vivienda',
				controller : ''
			})
			.when('/espacio', {
				templateUrl : 'espacio',
				controller : ''
			})
			.when('/nodo', {
				templateUrl : 'nodo',
				controller : ''
			})
			.when('/dispositivo', {
				templateUrl : 'dispositivo',
				controller : ''
			})
			.when('/dispositivoItem', {
				templateUrl : 'dispositivoItem',
				controller : ''
			})
			
			
			//Integrantes y Propietario
			
			.when('/listaEspacios', {
				templateUrl : 'listaEspacios',
				controller : ''
			})	
			
			
					
			.when('/jardin', {
				templateUrl : 'jardin',
				controller : ''
			})			
					
			
			.when('/perfil', {
				templateUrl : 'perfil'
			})
			
			.when('/acerca', {
				templateUrl : 'acerca'
			})	
			
			.otherwise({redirectTo:'/'})
            
			$locationProvider.html5Mode(false)
            
			$mdThemingProvider
        	.definePalette('DomoticaPalette', {
        		'50': '#a3d7a5',
                '100': '#92cf94',
                '200': '#80c883',
                '300': '#6ec071',
                '400': '#5cb860',
                '500': '#4CAF50',
                '600': '#449d48',
                '700': '#3d8b40',
                '800': '#357a38',
                '900': '#2d682f',
                'A100': '#b5dfb7',
                'A200': '#c7e7c8',
                'A400': '#d9eeda',
                'A700': '#255627',
                'contrastDefaultColor': 'light',    // whether, by default, text (contrast)
                                // on this palette should be dark or light
                'contrastDarkColors': ['50', '100', //hues which contrast should be 'dark' by default
                 '200', '300', '400', 'A100'],
                'contrastLightColors': undefined  
       		});
        	
        	$mdThemingProvider
        	.definePalette('customAccent', {
        		'50': '#19120f',
                '100': '#291d18',
                '200': '#392822',
                '300': '#49332b',
                '400': '#593f35',
                '500': '#694a3e',
                '600': '#896052',
                '700': '#996b5b',
                '800': '#a57868',
                '900': '#af8778',
                'A100': '#896052',
                'A200': '#795548',
                'A400': '#694a3e',
                'A700': '#b89588',
                'contrastDefaultColor': 'light',    // whether, by default, text (contrast)
                                // on this palette should be dark or light
                'contrastDarkColors': ['50', '100', //hues which contrast should be 'dark' by default
                 '200', '300', '400', 'A100'],
                'contrastLightColors': undefined  
       		});
        	
        	$mdThemingProvider
        	.definePalette('customWarn', {
        		'50': '#fbb4af',
                '100': '#f99d97',
                '200': '#f8877f',
                '300': '#f77066',
                '400': '#f55a4e',
                '500': '#F44336',
                '600': '#f32c1e',
                '700': '#ea1c0d',
                '800': '#d2190b',
                '900': '#ba160a',
                'A100': '#fccbc7',
                'A200': '#fde1df',
                'A400': '#fff8f7',
                'A700': '#a21309',
                'contrastDefaultColor': 'light',    // whether, by default, text (contrast)
                                // on this palette should be dark or light
                'contrastDarkColors': ['50', '100', //hues which contrast should be 'dark' by default
                 '200', '300', '400', 'A100'],
                'contrastLightColors': undefined  
       		});
            	
          
        $mdThemingProvider
        	.theme('default')
            .primaryPalette('DomoticaPalette')
            .accentPalette('customAccent')
            .warnPalette('customWarn')
            /**
            .accentPalette('brown', {
            	'default': '700' // use shade 200 for default, and keep all other shades the same
            });**/
      	            
    	});

	
	angular
	.module('administrador')
  	.directive('validNumber', function() {
  	    return {
  	        require : '?ngModel',
  	        link : function(scope, element, attrs, ngModelCtrl) {
  	            if (!ngModelCtrl) {
  	                return;
  	            }

  	            ngModelCtrl.$parsers.push(function(val) {

  	                if (angular.isUndefined(val)) {
  	                    var val = '';
  	                }

  	                var clean = val.replace(/[^0-9]/g, '');
  	                
  	                var negativeCheck = clean.split('-');
  	                var decimalCheck = clean.split('.');
  	                if (!angular.isUndefined(negativeCheck[1])) {
  	                    negativeCheck[1] = negativeCheck[1].slice(0,
  	                            negativeCheck[1].length);
  	                    clean = negativeCheck[0] + '-' + negativeCheck[1];
  	                    if (negativeCheck[0].length > 0) {
  	                        clean = negativeCheck[0];
  	                    }

  	                }

  	                if (!angular.isUndefined(decimalCheck[1])) {
  	                    decimalCheck[1] = decimalCheck[1].slice(0, 2);
  	                    clean = decimalCheck[0] + '.' + decimalCheck[1];
  	                }

  	                if (val !== clean) {
  	                    ngModelCtrl.$setViewValue(clean);
  	                    ngModelCtrl.$render();
  	                }
  	            

  	                return parseFloat(clean);
  	            });

  	            element.bind('keypress', function(event) {

  	                if (event.keyCode === 32) {

  	                    event.preventDefault();
  	                }
  	            });

  	        }
  	    };
  	});
  	
  	
  	
	
})(window.angular);

