angular.
module('input').
config(['$locationProvider', '$routeProvider', function($locationProvider, $routeProvider) {
	$routeProvider.
	when('/input', {
		templateUrl: 'resources/single/input/input.template.html',
		controller: 'inputController as vm',
		resolve: {
			Relationship: ['commonHttp', function (commonHttp) {
				return commonHttp.
					get('resources/single/input/data/select-relationship.json').
					then(function (result) {
						return result;
					});
			}]
		}
	});
}]);