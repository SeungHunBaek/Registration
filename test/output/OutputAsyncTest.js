var assert = require('assert');
var OutputAsync = require('../../src/output/OutputAsync');

describe('OutputAsync', function() {
  it ('print pdf', function() {
    new OutputAsync(process.stdout).process({ content: ["a"] });
  });
})
