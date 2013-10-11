// Generated by CoffeeScript 1.6.3
(function() {
  var ASMEditor, asm, data, json;

  if (typeof window === "undefined" || window === null) {
    json = require("./json3");
  } else {
    json = exports.types.json;
  }

  ASMEditor = (function() {
    function ASMEditor(snap) {
      this.snap = snap != null ? snap : {};
      this.history = [];
    }

    ASMEditor.prototype.getSpreadsheets = function() {
      var k, res, v, _ref;
      res = [];
      _ref = this.snap;
      for (k in _ref) {
        v = _ref[k];
        res.push(k);
      }
      return res;
    };

    ASMEditor.prototype.getSheets = function(spreadsheet) {
      var k, res, v, _ref;
      res = [];
      if (this.snap[spreadsheet] == null) {
        return res;
      }
      _ref = this.snap[spreadsheet];
      for (k in _ref) {
        v = _ref[k];
        res.push(k);
      }
      return res;
    };

    ASMEditor.prototype.getBlocks = function(spreadsheet, sheet) {
      if (this.snap[spreadsheet] == null) {
        return {};
      }
      if (this.snap[spreadsheet][sheet] == null) {
        return {};
      }
      return this.snap[spreadsheet][sheet]["blocks"];
    };

    return ASMEditor;

  })();

  data = {
    "SimplePricing.xlsx": {
      "vendor prices": {
        "blocks": {
          1: {
            name: "bolt",
            meaning: "http://bots.com/bolt.omdoc"
          }
        }
      },
      "customer prices": {
        "blocks": []
      }
    }
  };

  if (typeof WEB === "undefined" || WEB === null) {
    asm = new ASMEditor(data);
    console.log(asm.getSpreadsheets());
    console.log(asm.getSheets("test.xls"));
    console.log(asm.getBlocks("test.xls", "sheet1"));
  }

  if (typeof WEB !== "undefined" && WEB !== null) {
    window.asm = data;
    window.ASMEditor = ASMEditor;
  }

}).call(this);
