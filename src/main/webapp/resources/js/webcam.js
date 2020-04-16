(function() {
   var video = document.getElementById('video'),
      canvas = document.getElementById('canvas'),
      context = canvas.getContext('2d'),
      //photo = document.getElementById('photo'),
      vendorUrl = window.URL|| window.webkitURL,
      pic = document.getElementById("pic");

   navigator.getMedia = navigator.getUserMedia || navigator.webGetUserMedia
         || navigator.mozGetUserMedia || navigator.msGetUserMedia || navigator.webkitGetUserMedia ;

   // Capture

   navigator.getMedia({
      video : true,
      audio : false
   }, function(stream) {
      video.src = vendorUrl.createObjectURL(stream);
      video.play();

   }, function(error) {
      // ABC
   });

   function whenCapture(captureUrl) {
	  $('#video').css("display", 'none');
	  $('#btn_shoot').css("display", 'none');
      $('#pic').attr("src", captureUrl).css(
    		  "width",'400px',
    		  "heignt",'300px');
      $('#div_bottom').css("display", 'block');
   };

   function whenRetry() {
	   $('#video').css("display", 'block');
	   $('#btn_shoot').css("display", 'block');
	   $('#div_bottom').css("display", 'none');
	   $('#pic').removeAttr("src");
   };

   document.getElementById('btn_shoot').addEventListener('click', function() {
      context.drawImage(video, 0, 0, 400, 300);
      var captureUrl = canvas.toDataURL();
 	 $('#captureUrl').attr('value', captureUrl);


      whenCapture(captureUrl);
   });

   document.getElementById('btn_again').addEventListener('click', function() {
	   whenRetry();
	   });

})();