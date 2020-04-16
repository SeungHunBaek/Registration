angular.
module('webcam').
controller('webcamController', ['commonStore', 'pdfmake', 'nativeBridge', 'K_INPUT_DATA', 'K_TIMER_FLAG', '$scope',
function (commonStore, pdfmake, nativeBridge, INPUT_DATA, TIMER_FLAG, $scope) {
	var time1 = new Date().getTime();
	var vm = this;

	vm.showCameraDisplay = false;

	vm.btnShoot = btnShoot;
	vm.btnAgain = btnAgain;
	vm.agree = agree;

	vm.inputData = commonStore.get(INPUT_DATA);

	var video = document.getElementById('video'),
    canvas = document.getElementById('canvas'),
    context = canvas.getContext('2d'),
    vendorUrl = window.URL|| window.webkitURL,
    pic = document.getElementById("pic");

	var localMediaStream  = null;

	navigator.getMedia = navigator.getUserMedia || navigator.webGetUserMedia
       || navigator.mozGetUserMedia || navigator.msGetUserMedia || navigator.webkitGetUserMedia;

	navigator.getMedia({
	      video : true,
	      audio : false
    }, function(stream) {


        video.src = vendorUrl.createObjectURL(stream);
        video.play();

        var time2 = new Date().getTime();
        console.log('[PERF] [webcam] show camera: ' + (time2 - time1) + ' ms');

        localMediaStream = stream;

        vm.showCameraDisplay = true;
        $scope.$apply();
    }, function(error) {
        // ABC
    });

	function whenCapture(captureUrl) {
		  $('#video').css("display", 'none');
		  $('#btn_shoot').css("display", 'none');
	      $('#pic').attr("src", captureUrl).css(
	           "width",'44%',
	    	   "heignt",'86%').css("display", "");
	      $('#div_bottom').css("display", 'block');
	}

	function whenRetry() {
		  $('#video').css("display", '');
		  $('#btn_shoot').css("display", 'inline-block');
		  $('#div_bottom').css("display", 'none');
		  $('#pic').removeAttr("src").css("display", "none");
	}

	function btnShoot() {
		context.translate(canvas.width, 0);
		context.scale(-1, 1);
		context.drawImage(video, 0, 0, 400, 300);
	    vm.captureUrl = canvas.toDataURL();

	    whenCapture(vm.captureUrl);
	}

	function btnAgain() {
		context.translate(canvas.width, 0);
		context.scale(-1, 1);
		whenRetry();
	}

	function agree() {
		$('#btn_again').attr('disabled', 'disabled').css('background-color', 'rgb(169,169,169');
		$('#btn_ok').attr('disabled', 'disabled').css('background-color', 'rgb(169,169,169');

		commonStore.add(TIMER_FLAG, false);
		vm.inputData.cameraImgData = vm.captureUrl;

		var storeId = localStorage.getItem('storeId');
		vm.inputData.storeId = storeId;

		var time1 = new Date().getTime();
		pdfmake.make(vm.inputData).
		then(function(blob) {
			var time2 = new Date().getTime();
			console.log('[PERF] [make] get blob: ' + (time2 - time1) + ' ms');
			return blob;
		}).
		then(pdfmake.toDataUrl).
		then(function (dataUrl) {

			commonStore.add(TIMER_FLAG, true);
			commonStore.remove(INPUT_DATA);

			nativeBridge.transferPdf(dataUrl);
		});
	}

	(function() { })();
}]);