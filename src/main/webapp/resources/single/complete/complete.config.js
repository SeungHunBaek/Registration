angular.
module('complete').
config(['$locationProvider', '$routeProvider', function($locationProvider, $routeProvider) {
	$routeProvider.
	when('/complete', {
		templateUrl: 'resources/single/complete/complete.template.html',
		controller: 'completeController as vm'
	});
}]);