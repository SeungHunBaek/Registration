angular.
module('common').
directive('commonSelect',
function() {
	return {
		templateUrl: 'resources/single/common/template/common-select.template.html',
		restrict: 'E',
		scope: {
			item: '=',
			id: '@',
			label: '@',
		}
	};
});