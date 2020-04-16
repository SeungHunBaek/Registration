"use strict";

/**
 * 非同期処理 (入力)
 */
class InputAsync {

  /**
   * InputAsync(input) コンストラクタ
   * @param {ReadableStream} input 入力ストリーム
   */
  constructor(/* ReadableStream */ input) {
    /**
     * メンバー: 入力ストリーム
     * @type {ReadableStream}
     */
    this.input = input;
  }

　/**
   * 内部関数 "processInputData"を呼び出す。
   * (入力ストリームから入力データを取得する。)
   * @returns {Promise} 非同期処理
   */
  /* Promise */ process() {
    return processInputData(this.input);
  }
}

/**
 * 入力ストリームから入力データを取得する。
 * @param   {ReadableStream} 入力ストリーム
 * @returns {Promise} 非同期処理
 */
function processInputData(/* ReadableStream */ input) {
  var promise = new Promise(function (resolve, reject) {
    var body = [];
    input.resume();

    // データを読み込む際
    input.on("data", function (chunk) {
      body.push(chunk);
    });

    // 読み込み処理が終わったら
    input.on("end", function() {
      // JSON文字列をObjectに変更し、次の処理に渡す。
      resolve(JSON.parse(Buffer.concat(body).toString()));
    });
  });

  return promise;
}

module.exports = InputAsync;
