let express = require('express');
let request = require('request');
let app  = express();
let bodyParser = require("body-parser");


app.use(express.static("./"));
app.use(bodyParser.urlencoded({extended:false}));
app.use(bodyParser.json());

app.get('/login.html',function (req,res) {
    res.sendfile("login.html")
});

app.listen(8080, function () {
    console.log("Server starting port 8080...");
});



