angular.
module('registrationWebApp').
config(['$locationProvider', '$routeProvider', function($locationProvider, $routeProvider) {
	$locationProvider.hashPrefix('app');
	$routeProvider.otherwise('/');
}]);