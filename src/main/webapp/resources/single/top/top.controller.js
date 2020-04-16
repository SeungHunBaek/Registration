angular.
module('top').
controller('topController', function($scope) {
	var vm = this;
	$(function() {
		if (typeof localStorage === 'object') {
			try {
				var storeId = localStorage.getItem('storeId');
				if(storeId == null){
					location.href="#app/config";
				}
			} catch (e) {
				consol.log('fail');
			}
		}

	});
});