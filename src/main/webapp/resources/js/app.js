var wrapper = document.getElementById("signature-pad"),
    clearButton = wrapper.querySelector("[data-action=clear]"),
    savePNGButton = wrapper.querySelector("[data-action=save-png]"),
//    saveSVGButton = wrapper.querySelector("[data-action=save-svg]"),
    canvas = wrapper.querySelector("canvas"),
    sign = document.getElementById("sign"),
    signaturePad
    ;


// Adjust canvas coordinate space taking into account pixel ratio,
// to make it look crisp on mobile devices.
 // This also causes canvas to be cleared.
 function resizeCanvas() {
     // When zoomed out to less than 100%, for some very strange reason,
     // some browsers report devicePixelRatio as less than 1
     // and only part of the canvas is cleared then.
     var ratio =  Math.max(window.devicePixelRatio || 1, 1);
    canvas.width = canvas.offsetWidth * ratio;
    canvas.height = canvas.offsetHeight * ratio;
    canvas.getContext("2d").scale(ratio, ratio);
 }


 window.onresize = resizeCanvas;
 resizeCanvas();


 signaturePad = new SignaturePad(canvas);


 clearButton.addEventListener("click", function (event) {
     signaturePad.clear();
 });

 sign.addEventListener("click", function (event){
	 $('#openModal').css('display', 'block');
 });


 savePNGButton.addEventListener("click", function (event) {
     if (signaturePad.isEmpty()) {

         alert("Please provide signature first.");
     } else {
    	 var imgUrl = signaturePad.toDataURL();
    	 whenSuccess(imgUrl);
     }
 });

 function whenSuccess(imgUrl) {
	// $('#sign').attr("src", imgUrl).css("width",'500px',"heignt", '300px');
	 $('#openModal').css('display', 'none');
	 $('#imgTest').attr('value', imgUrl);

 }

