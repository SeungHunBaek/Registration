angular.
module('top').
config(['$locationProvider', '$routeProvider', function($locationProvider, $routeProvider) {
	$routeProvider.
	when('/', {
		templateUrl: 'resources/single/top/top.template.html',
		controller: 'topController as vm'
	});
}]);