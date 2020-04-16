angular.
module('consent').
controller('consentController', ['K_TODAY', 'commonStore', 'privacyHTML',
function(K_TODAY, commonStore, privacyHTML) {
	var vm = this;

	vm.getToday = getToday;
	vm.privacyHTML = privacyHTML;

	function getToday() {
		var today = new Date().toJSON().slice(5,10).replace(/-/g,'/') + "/" + new Date().toJSON().slice(0,4)

		return today;
	}

	(function() {
		vm.today = vm.getToday();
		commonStore.add(K_TODAY, vm.today);

		vm.agree = {};
		vm.agree.disabled = true;
	})();
}]);