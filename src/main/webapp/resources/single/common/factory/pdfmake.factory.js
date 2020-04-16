angular.
module('common').
factory('pdfmake', ['$q', 'K_PDFMAKE_URL', 'commonHttp', function($q, PDFMAKE_URL, commonHttp) {
	var factory = {};

	/** make pdf file */
	factory.make = function (inputData) {
		var data = {
			list: [
				{
					name: inputData['list[0].name'] || '',
					gender: inputData['list[0].gender'] || '',
					birthDay: inputData['list[0].birthDay'] || '',
				},
				{
					name: inputData['list[1].name'] || '',
					gender: inputData['list[1].gender'] || '',
					birthDay: inputData['list[1].birthDay'] || '',
				}
			],
			today:         inputData.today || '',
			parentName:    inputData.parentName || '',
			address:       inputData.address || '',
			city:          inputData.city || '',
			phone:         inputData.countryCode + inputData.phone || '',
			state:         inputData.state || '',
			zipCode:       inputData.zipCode || '',
			relationship:  inputData.relationship || '',
			signImgData:   inputData.signImgData || '',
			cameraImgData: inputData.cameraImgData || '',
			storeId:       inputData.storeId
		};

		return commonHttp.postToBlob(PDFMAKE_URL, data);
	};

	/** open pdf */
	factory.open = function (blob) {
		var url = window.URL.createObjectURL(blob);
		window.open(url, '_blank');
	}

	/** blob -> todataurl */
	factory.toDataUrl = function (blob) {
		var deferred = $q.defer();
		var reader = new FileReader();
		reader.onload = function(e) { deferred.resolve(e.target.result); };
		reader.readAsDataURL(blob);

		return deferred.promise;
	}

	return factory;
}]);