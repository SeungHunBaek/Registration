angular.
module('config').
controller('configController', function($scope) {
	$(function() {
		$('#newStoreId').bind('click', function() {
			var newStoreId = $('#storeId').val();
			localStorage.setItem('storeId', newStoreId);
		});
	});
});



