var express = require('express');
var proxy = require('http-proxy-middleware');

var app = express();
app.use('/', express.static('.'));

 
app.listen(3000);

/**
 * 开发时
 * 先 node index.js
 * 再访问
 * http://localhost:3000/index.html#YWRtaW4=
 */
