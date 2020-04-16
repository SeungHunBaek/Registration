/*
 * in memory store
 */

angular.
module('common').
factory('commonStore', function() {
	var commonStore = {};

	function add(key, value) {
		commonStore[key] = value;
	}

	function get(key) {
		return commonStore[key];
	}

	function remove(key) {
		commonStore[key] = undefined;
		delete commonStore[key];
	}

	function clear() {
		var keys = Object.keys(commonStore);

		keys.forEach(function(k) {
			remove(k);
		});
	}

	return {
		add: add,
		get: get,
		remove: remove,
		clear: clear
	};
});