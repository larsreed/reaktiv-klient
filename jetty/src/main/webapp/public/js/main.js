var plottebord = (function($){

"use strict";

var containers = {};
var containersChangeListeners = [];
var connection = new WebSocket("ws://localhost:8080/containers");

connection.onmessage = function (event) {
  if (event.data !== 'empty') {
    connection.send(JSON.stringify(createContainers()));
  } else {
    containers = JSON.parse(event.data);
    containersChangeListeners.forEach(function (listener) {
      listener();
    });
  }
};

function addContainersChangeListener(listener) {
  containersChangeListeners.push(listener);
}

return {
  createSection: createSection,
  createSpaces: createSpaces,
  addContainersChangeListener: addContainersChangeListener
};

})(jQuery);
