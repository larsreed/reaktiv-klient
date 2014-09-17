(function($){

  "use strict";

  var form = $("form");
  var input = form.find('input[name="url"]');
  var debug = $("#debug");
  var results = $("#results");
  var imagecount = $("#imagecount");

  function handleSubmit() {
    serverConnection.send({
      url: input.val().trim()
    });
    results.html("");
    return false;
  }

  function handleMessage(message) {
    debug.html(JSON.stringify(message));

    var images = message.images;
    var i = images.length;
    var toInsert = "";
    for (;i--;) {
      toInsert = toInsert + '<a href="#" class="list-group-item"><span class="helper"></span><img src="'
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

  function toggleShowClass() {
    $(this).toggleClass("show");
  }

  results.delegate("a.list-group-item", "click", toggleShowClass);

  form.submit(handleSubmit);
  serverConnection.setMessageHandler(handleMessage);

})(jQuery);
