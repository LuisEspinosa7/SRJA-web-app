(function(){
	'use strict';

	angular.module('passwordConfirmation.directive',['ngMaterial', 'ngMessages'])
	
	.directive('confirmPwd', function($interpolate, $parse) {
		  return {
		    require: 'ngModel',
		    link: function(scope, elem, attr, ngModelCtrl) {

		      var pwdToMatch = $parse(attr.confirmPwd);
		      var pwdFn = $interpolate(attr.confirmPwd)(scope);

		      scope.$watch(pwdFn, function(newVal) {
		          ngModelCtrl.$setValidity('password', ngModelCtrl.$viewValue == newVal);
		      })

		      ngModelCtrl.$validators.password = function(modelValue, viewValue) {
		        var value = modelValue || viewValue;
		        return value == pwdToMatch(scope);
		      };

		    }
		  }
		});

})();