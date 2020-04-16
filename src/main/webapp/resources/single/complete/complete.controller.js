angular.
module('complete').
controller('completeController', [ 'K_TIMER_FLAG','commonStore',
function(TIMER_FLAG, commonStore) {
	commonStore.add(TIMER_FLAG, true);
}]);