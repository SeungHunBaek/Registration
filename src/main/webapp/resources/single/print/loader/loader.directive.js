angular.
module('print').
directive('loader', [ 'commonHttp',
function(commonHttp) {
	return {
		templateUrl: 'resources/single/print/loader/loader.template.html',
		restrict: 'E'
	};
}]);