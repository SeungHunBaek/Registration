angular.
module('input').
directive('telInput', [
function() {
	function link(scope, elem, attr) {
		var input = $(elem);
		input.intlTelInput({ utilsScript: "resources/js/utils.js" });

		scope.onLoad(input[0]);
	}

	return {
		templateUrl: 'resources/single/input/tel-input/tel-input.template.html',
		restrict: 'E',
		link: link,
		replace: true,
		scope: {
			inputId : '@',
			inputName: '@',
			inputPlaceholder: '@',
			inputAutocomplete: '@',
			onLoad: '='
		}
	}
}]);