"use strict";
const InputAsync    = require("./src/input/InputAsync");
const DocDefinition = require("./src/pdf/DocDefinition");
const OutputAsync   = require("./src/output/OutputAsync");

const input  = process.stdin;
const output = process.stdout;

void function main(args) {
  /*
   * 1. 入力処理
   * 2. PDFドキュメント定義
   * 3. 出力処理
   */
   new InputAsync(input).process()
   .then(function (inputData) {
     return new DocDefinition(inputData).get();
   })
   .then(function (docDefinition) {
     new OutputAsync(output).process(docDefinition);
   });
}
(process.argv.slice(2));
