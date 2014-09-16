var serverConnection = (function($){

  "use strict";

  var connection = new WebSocket("ws://localhost:8080/pageparser");
  var messageHandler = null;

  connection.onmessage = function (event) {
    var message = tryParseJSON(event.data);
    if (message && typeof messageHandler === "function") {
      messageHandler(message);
    }
  };


  function tryParseJSON (jsonString){
      try {
          var o = JSON.parse(jsonString);

          // Handle non-exception-throwing cases:
          // Neither JSON.parse(false) or JSON.parse(1234) throw errors, hence the type-checking,
          // but... JSON.parse(null) returns 'null', and typeof null === "object",
          // so we must check for that, too.
          if (o && typeof o === "object" && o !== null) {
              return o;
          }
      }
      catch (e) { }

      return false;
  }

  return {
    setMessageHandler: function(f) {
      messageHandler = f;
    },
    send: function(jsonPackage) {
      connection.send(JSON.stringify(jsonPackage));
    }
  };

})(jQuery);
