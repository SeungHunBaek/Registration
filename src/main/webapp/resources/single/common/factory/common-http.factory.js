angular.
module('common').
factory('commonHttp', ['$q', '$http', 'commonStore',
function ($q, $http, commonStore) {

	function get(url) {
		var deferred = $q.defer();
		var data = commonStore.get("GET: " + url);

		if (typeof data === 'undefined') {
			$http.
			get(url).
			then(function(result) {
				if (result.status === 200) {
					commonStore.add("GET: " + url, result.data);
					data = commonStore.get("GET: " + url);

					deferred.resolve(data);
				}
			});
		} else {
			deferred.resolve(data);
		}

		setTimeout(clearCache("GET: " + url), 30000);

		return deferred.promise;
	}

	function postToBlob(url, data) {
		var deferred = $q.defer();
		var request = new XMLHttpRequest();

		request.open('post', url, true);

		request.responseType = 'blob';

		request.setRequestHeader('Content-Type', 'application/json');
		request.onreadystatechange = function onReadyStateChange() {
			if (request.readyState === 4 && request.status === 200) {
				deferred.resolve(request.response);
			}
		};
		request.send(JSON.stringify(data));

		return deferred.promise;
	}

	function clearCache(key) {
		return function() { commonStore.remove(key); };
	}

	return {
		get: get,
		postToBlob: postToBlob
	};
}]);