angular.
module('consent').
config(['$locationProvider', '$routeProvider', function($locationProvider, $routeProvider) {
	$routeProvider.
	when('/consent', {
		templateUrl: 'resources/single/consent/consent.template.html',
		controller: 'consentController as vm',
		resolve: {
			privacyHTML: ['commonHttp',
			function(commonHttp) {
				return commonHttp.get('resources/single/consent/templates/privacy.template.html');
			}]
		}
	});
}]);