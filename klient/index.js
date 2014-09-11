var app = require("express")();
var http = require("http").Server(app);
var io = require("socket.io")(http);
var static = require('node-static');

var file = new static.Server('./html');

require('http').createServer(function (request, response) {
    request.addListener('end', function () {
        file.serve(request, response);
    }).resume();
}).listen(8080);

io.on('connection', function() {
  console.log("Socket.IO connection");
});

http.listen(3000, function() {
  console.log("Listening to port 3000.");
});
