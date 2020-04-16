angular.
module('consent').
directive('appPrivacyScroll', function() {
	var link = function (scope, element, attrs) {
		var $privacy = $('#privacy-terms'),
	    	$privacyWrapper = $('#privacy-wrapper'),
	    	$agree = $('#i-agree'),
	    	height = $privacy.height(),
	    	top = $privacy.offset().top;

		$privacy.on('scroll', function() {
			var privacyWrapperHeight = $privacyWrapper.outerHeight();

			if (Math.ceil(height - $privacyWrapper.offset().top + top) >= privacyWrapperHeight ) {
				 $agree.prop('disabled', false);
			}
		});
	};

	return {
		restrict: 'A',
		link: link
	};
});