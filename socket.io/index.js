var express = require("express");
var app = express();
var http = require("http").Server(app);
var io = require("socket.io")(http);

app.use(express.static('./html'));

io.on('connection', function() {
  console.log("Socket.IO connection");
});

http.listen(3000, function() {
  console.log("Listening to port 3000.");
});
