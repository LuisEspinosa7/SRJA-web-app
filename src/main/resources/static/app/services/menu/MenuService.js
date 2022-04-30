(function() {
	'use strict';

	angular.module('menu.service').service('menuService', [ '$q', function($q) {
		var menu = [{
			name : 'Medicos',
			icon : 'fa fa-list',
			type : 'toggle',
			content : [ 
			{
				name : 'Inscripcion Medico',
				icon : 'fa fa-list',
				type : 'link',
				url : 'inscripcionMedico'
			
			}, 
			{
				name : 'Gestion Medicos',
				icon : 'fa fa-pied-piper',
				type : 'link',
				url : 'medicos'

			}]
		}		
		
		,{
			name : 'Acerca de',
			icon : 'fa fa-ticket',
			type : 'link',
			url : 'acerca'
		}		
		
		];

		return {
			loadAllMenu : function() {
				return $q.when(menu);
			}
		}
	} ]);

})();