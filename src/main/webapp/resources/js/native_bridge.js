/**
 * http://usejsdoc.org/
 */

void function (module, exports) {
	var bridge = {};

	bridge.transferPdf = function(file) {
		bridge.call('transferPdf', JSON.stringify({file: file}));
	}

	bridge.injectSelect = function() {
		var selectTags = Array.prototype.slice.call(document.getElementsByTagName('select'), 0);
		selectTags.forEach(function(sel) {
			sel.onmousedown = function(e) {
				 e.preventDefault();
				 this.blur();
				 window.focus();

				 return false;
		 	};

		 	sel.onclick = function(e) {
				 e.preventDefault();
				 var title = this.title;
				 var id = this.id;
				 var selectedIndex = this.options.selectedIndex;
				 var options = [];
				 for (var i=0; i<this.options.length; i++) {
				 	options.push(this.options[i].firstChild.nodeValue);
				 }

				 var args = JSON.stringify({title: title, id: id, selectedIndex: selectedIndex, options: options });
				 //alert('native:nativeSelect(' + args + ')');
				 bridge.call('nativeSelect', args);

				 return false;
			 };
		});
	}

	bridge.call = function(fn, args) {
		alert('native:' + fn + '(' + args + ')');
	}

	bridge.forward = function (path) {
		location.href = path;
	}

	exports.bridge = bridge;
}({ exports: window }, window);