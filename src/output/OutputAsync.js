"use strict";
var pdfPrinter = require("pdfmake/src/printer");

// フォント定義 (該当するフォントのファイルパスを指定する。)
const fonts = {
    Roboto: {
        normal:      "./fonts/Roboto-Regular.ttf",
        bold:        "./fonts/Roboto-Medium.ttf",
        italics:     "./fonts/Roboto-Italic.ttf",
        bolditalics: "./fonts/Roboto-Italic.ttf"
    }
};

/**
 * PDFmakeの出力処理
 * @type {pdfPrinter}
 * @private
 */
var printer = new pdfPrinter(fonts);

/**
 * 非同期処理 (出力)
 */
class OutputAsync {
  /**
   * InputAsync(input) コンストラクタ
   * @param {WritableStream} output 出力ストリーム
   */
  constructor (output) {
    /**
     * メンバー: 出力ストリーム
     * @type {WritableStream}
     */
    this.output = output;
  }

  /**
   * PDFデータを出力ストリームに送信する。
   * @param {Object} docDefinition ドキュメント定義(pdfmake)
   */
  process (docDefinition) {
    // pdfmakeのプリンタオブジェクトを生成
    var pdfDoc = printer.createPdfKitDocument(docDefinition);

    // 出力先指定
    pdfDoc.pipe(this.output);

    // データ送信
    pdfDoc.end();
  }
}

module.exports = OutputAsync;
