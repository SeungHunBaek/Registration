angular.
module('input').
directive('signaturePad', ["K_TODAY", "K_INPUT_DATA", "commonStore",
function(K_TODAY, K_INPUT_DATA, commonStore) {

	function compile(element, attrs) {
		var canvas = $('#canvas1', element)[0];
		var sign = $('#page-input #sign')[0];
		var savePNGButton = $("[data-action=save-png]", element)[0];
		var signaturePad;
		var form = $('#page-input form');
		// Adjust canvas coordinate space taking into account pixel ratio,
		// to make it look crisp on mobile devices.
		// This also causes canvas to be cleared.
		function resizeCanvas() {
		    // When zoomed out to less than 100%, for some very strange reason,
		    // some browsers report devicePixelRatio as less than 1
		    // and only part of the canvas is cleared then.
		    var ratio =  Math.max(window.devicePixelRatio || 1, 1);
		    canvas.width = canvas.offsetWidth * 1;
		    canvas.height = canvas.offsetHeight * 0.9;
		    canvas.getContext("2d").scale(1, 1);
		}

		element.on('resize', resizeCanvas);
		resizeCanvas();


		signaturePad = new SignaturePad(canvas);

		sign.addEventListener("click", function (event){
			 $('#page-input #openModal').css('display', 'block');
		});

		savePNGButton.addEventListener("click", function (event) {
		     if (signaturePad.isEmpty()) {
		         alert("Please provide signature first.");
		     } else {
		    	 var imgUrl = signaturePad.toDataURL();
		    	 var inputs = $('input, select', form);

		    	 var inputData = {
		    		signImgData: imgUrl
		    	 };

		    	 inputs.each(function() {
		    		 var $this = $(this);
		    		 var type = $this.attr('type');

		    		 if ( (type === 'checkbox' || type === 'radio') && (!this.checked)) {
		    			 return;
		    		 }

		    		 inputData[$this.attr('name')] = $this.val();
		    	 });

		    	 inputData.today = commonStore.get(K_TODAY);
		    	 commonStore.remove(K_TODAY);

		    	 commonStore.add(K_INPUT_DATA, inputData);

		    	 location.href = "#app/webcam";
		     }
		});
	};

	return {
		templateUrl: 'resources/single/input/signature-pad/signature-pad.template.html',
		restrict: 'E',
		compile: compile,


	}
}]);