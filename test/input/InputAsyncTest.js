var assert = require('assert');
var Readable = require('stream').Readable;

var InputAsync = require('../../src/input/InputAsync');

var input = new Readable();

var data = JSON.stringify({test: 'OK'});
input.push(data);
input.push(null);

console.log("input data is: ", data);

describe('InputAsync', function() {
  var inputAsync;

  beforeEach(function () {
    inputAsync = new InputAsync(input);
  });

  describe('constructor', function () {
    it('InputAsync should be created', function () {
      assert.notEqual(inputAsync, undefined);
      assert.notEqual(inputAsync, null);
    });
  });

  describe('process', function() {
    var promise;
    it('process() should be retrun "Promise" Object', function() {
      promise = inputAsync.process();
      assert.ok(promise instanceof Promise);
    });

    it('process().then() callback should owns 1 argument', function() {
      promise.then(function() {
        assert.deepEqual(arguments.length, 1);
      });
    });

    it('process().then() callback argument[0] should be {test: "OK"} Object', function() {
      promise.then(function(arg0) {
        var expected = {test: 'OK'};
        assert.deepEqual(arg0, expected);
      });
    });
  });
});
