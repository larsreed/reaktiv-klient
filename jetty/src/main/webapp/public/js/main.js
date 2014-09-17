(function($){

  "use strict";

  var form = $("form");
  var input = form.find('input[name="url"]');
  var debug = $("#debug");
  var results = $("#results");
  var stopbutton = $("#stopbutton");
  var imagecount = $("#imagecount");

  function handleSubmit() {
    serverConnection.send({
      action: "parse",
      url: input.val().trim()
    });
    results.html("");
    stopbutton.show();
    return false;
  }

  function handleStop() {
    serverConnection.send({
      action: "stop"
    });
    return false;
  }

  function addImages(images) {
    if (!images && typeof images !== "object") {
      return;
    }
    var i = images.length;
    var toInsert = "";
    for (;i--;) {
      toInsert = toInsert
        + '<a href="#" class="list-group-item"><span class="helper"></span><img src="'
        + images[i]
        + '"><p class="list-group-item-text">'
        + images[i]
        + '</p></a>';
    }
    if (toInsert.length > 0) {
      results.prepend(toInsert);
      imagecount.html(results.find("a.list-group-item").size());
    }
  }

  function handleMessage(message) {
    debug.html(JSON.stringify(message));

    var type = message.type;
    if (typeof type !== "string") {
      return;
    }

    if (type === "images") {
      addImages(message.images);
    }
    else if (type === "stopped") {
      stopbutton.hide();
    }
  }

  function toggleShowClass() {
    $(this).toggleClass("show");
  }

  results.delegate("a.list-group-item", "click", toggleShowClass);

  form.submit(handleSubmit);
  stopbutton.click(handleStop);

  serverConnection.setMessageHandler(handleMessage);

})(jQuery);
