angular.
module('common').
factory('nativeBridge', function() {
	var bridge = {};

	bridge.transferPdf = function(file) {
		bridge.call('transferPdf', JSON.stringify({file: file}));
	}

	bridge.call = function(fn, args) {
		alert('native:' + fn + '(' + args + ')');
	}

	return bridge;
});