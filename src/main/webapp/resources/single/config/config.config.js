angular.
module('config').
config(['$locationProvider', '$routeProvider', function($locationProvider, $routeProvider) {
	$routeProvider.
	when('/config', {
		templateUrl: 'resources/single/config/config.template.html',
		controller: 'configController as vm'
	});
}]);