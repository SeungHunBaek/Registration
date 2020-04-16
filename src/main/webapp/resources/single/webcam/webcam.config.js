angular.
module('webcam').
config(['$locationProvider', '$routeProvider', function($locationProvider, $routeProvider) {
	$routeProvider.
	when('/webcam', {
		templateUrl: 'resources/single/webcam/webcam.template.html',
		controller: 'webcamController as vm'
	});
}]);