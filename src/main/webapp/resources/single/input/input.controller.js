angular.
module('input').
controller('inputController',['Relationship', function(relationship) {
	var vm = this;
	var no = $('#page-input input[type="text"],#page-input [name="relationship"], #page-input input[id="zipcode"]');
	var signFlag = false;



	$('#page-input input[type="date"]').change(function(){
		$('#page-input input[type="date"]').blur();

		setTimeout(function () {
			no.eq(2).focus()},100);
	});

	$('#page-input [name="relationship"]').change(function(){
		no.eq(4).focus();
		lastCheck();
	});

	no.eq(no.index(7)).blur(function () {
			$('#page-input #phone').focus();
	});

	no.keypress(function(e) {

		if (e.which == 13) {/* 13 == enter key@ascii */
			if(no.index(this) < 2){
				no.eq(no.index(this)).blur();
			} else if(no.index(this) === 2) {
				$('#page-input [name="relationship"]').focus();
			} else {
				no.eq(no.index(this) + 1).focus();
			}
		}
	});

	vm.focus = function focus(telInput) {
		$("#page-input .block-gender-values").removeClass("focus");

		setTimeout(function () {
			$('#page-input [name="relationship"] option:selected').hide()},100);

		var s0p = $('#page-input');
		var s0 = $('input[type=text], input[type=number], input[type=tel]', s0p);

		// force focusing
		s0.on('focus',function(e) {
			var offsetTop  = this.offsetTop;
			var top = offsetTop - 100;

			if (top < 0) {
				top = this.parentNode.offsetTop - 100;
			}
			window.scrollTo(0, top);
		});

		no.push(telInput);


		no.keyup(function(){

			if($("#participantName1").val()==""){
				$("input[name='list[1].gender").each(function(i) {
		            $(this).attr('disabled', true);
		            $("#birthDay1").attr('disabled', true);
		        });
			}else{
				$("input[name='list[1].gender").each(function(i) {
		            $(this).attr('disabled', false);
		            $("#birthDay1").attr('disabled', false);
		        });
			}
			lastCheck();
		});
	}




	$('#page-input input[type="radio"], input:checkbox[id="infoCheck"]').on('change', function() {
		lastCheck();
	});

	$('#page-input input:checkbox[id="infoCheck"]').click(function () {
		$('#page-input input:checkbox[id="infoCheck"]').blur();
	});

	$('#lbl_check').click(function(){
		if($('#infoCheck').is(':checked') == true){
			$('#page-input canvas').removeClass('signFocus');
		}else{
			$('#page-input canvas').addClass('signFocus');
		}
		lastCheck();
	});

	$('#onDraw').bind('touchstart',function () {
		signFlag = true;
		lastCheck();
	});

	$("#page-input #infoCheck").change(function() {
	    if(this.checked) {
	    	$('#page-input canvas').addClass('signFocus');
	    } else {
	    	$('#page-input canvas').removeClass('signFocus');
	    }
	});


	function lastCheck(){
		if(invalidate() && signFlag && $('#page-input [name="relationship"] option:selected').val() != "select"){
			$("#nextButton").attr('disabled', false);
			$("#nextButton").attr("class","button_red");
		}else{
			$("#nextButton").attr('disabled', true);
			$("#nextButton").attr("class","button_gray");
		}
	}

	function invalidate(){
		var countryCode = "(+" + $("#phone").intlTelInput("getSelectedCountryData").dialCode + ")";
		if (countryCode) {
			$('input[name=countryCode]').val(countryCode);
		} else {
			return false;
		}

		if(!$('input:radio[name="agree"]').is(":checked") ||
				!$('input:checkbox[id="infoCheck"]').is(":checked") ||
				$('input:radio[name="agree"]:checked').val()=="no"){
			return false;
		}
		if($('#participantName0').val() == "" ||
				!$('input:radio[name="list[0].gender"]').is(":checked") ||
				$('#birthDay0').val() == ""){
			return false;
		}
		if($('#participantName1').val() != ""){
			if(!$('input:radio[name="list[1].gender"]').is(":checked")){
				return false;
			}
			if($('#birthDay1').val() == ""){
				return false;
			}
		}

		if($('#parentName').val()== "" ||  $('#address').val()== "" || $('#city').val() == "" ||
				$('#state').val()== "" || $('#zipcode').val()== "" || $('#phone').val() == ""){
				return false;
		}



		return true;
	}

	// show focus indicator for Gender field
	var beforeFocusedId = '';
	$('#page-input input[id^="participantName"]').blur(function() {
		if (!this.value) {
			return;
		}

		var id = this.id;
		var selector = "";

		if (id === 'participantName0') {
			selector = '#page-input .participant-0.block-gender-values';
		} else if (id === 'participantName1') {
			selector = '#page-input .participant-1.block-gender-values';
		}

		$(selector).addClass('focus');
		beforeFocusedId = id;
	});

	$('#page-input input,#page-input [name="relationship"]').focusin(function() {
		var id = this.id;

		if (Boolean(id) === true) {
			$('#page-input span, #page-input [name="relationship"]').removeClass('focus');
		}
	});

	$('#page-input input[name^="list"],#page-input [name="agree"]').click(function () {
		$('#page-input span, #page-input [name="relationship"]').removeClass('focus');
	});



	$('#page-input input[name$="gender"]').change(function() {
		if (!this.value) {
			return;
		}

		var radioId = this.name;
		var dateSelector = "";

		if (radioId === 'list[0].gender') {
			dateSelector = '#page-input [name="list[0].birthDay"]';
		} else if (radioId === 'list[1].gender') {
			dateSelector = '#page-input [name="list[1].birthDay"]';
		} else {
			dateSelector = 'none';
		}
		$(dateSelector).focus();
	});

	$('#page-input input,#page-input [name="relationship"]').focusin(function() {
		var id = this.id;

		if (Boolean(id) === true) {
			$('#page-input canvas').removeClass('signFocus');
		}
	});

	$('#page-input input[name^="list"],#page-input [name="agree"]').click(function () {
		$('#page-input canvas').removeClass('signFocus');
	});

	(function() {
		vm.relationship = relationship;
	})();
}]);