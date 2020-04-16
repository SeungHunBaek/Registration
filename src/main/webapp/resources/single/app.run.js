angular.
module('registrationWebApp').
run(['K_INPUT_TIMEOUT_SECONDS', 'K_TIMER_FLAG', 'commonStore',
function (TIMEOUT, TIMER_FLAG, commonStore) {
	var seconds = 0;
	var pageNameBefore = '';
	var dateCheck = true;

	$(function() {
		setInterval(onTimer, 1000);

		// reset timer by mouse or touchpad interaction.
		window.addEventListener('mousemove', resetTimer);
		window.addEventListener('touchmove', resetTimer, {passive: true});

		// hardware & screen keyboard input
		window.addEventListener('keydown', resetTimer);
	});

	commonStore.add(TIMER_FLAG, true);

	function onTimer() {
		$(".input_date_size").focus(function(){
			dateCheck= false;
		});

		$(".input_text_size").focus(function(){
			dateCheck= true;
		});

		if(dateCheck){
			// check timerflag
			var timerFlag = commonStore.get(TIMER_FLAG);
			if (!timerFlag) {
				resetTimer();
				return;
			}

			var url = location.href;
			var pageName = url.substring(url.lastIndexOf('/') + 1, url.length);

			// reset timer after page is changed
			if (pageNameBefore !== pageName) {
				resetTimer();
				pageNameBefore = pageName;
			}

			// top page must reset a timer
			// others are increase timer seconds.
			if (pageName === '') {
				resetTimer();
			} else {
				seconds += 1;

				// Variable seconds is reached timeout, Page will return top.
				if (seconds === TIMEOUT) {
					location.href = '#app/top';
				}
			}
		}
	}

	function resetTimer() {
		seconds = 0;
	}
}]);