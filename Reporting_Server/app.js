const express = require('express');
const app = express();
const MongoClient = require('mongodb');
const assert = require('assert');
var myParser = require("body-parser");

var url = "mongodb://localhost:27017/reportingDB";

// MongoClient.connect(url, function(err, db) {
//     if (err) throw err;
//     console.log("Database created!");
//     db.close();
//   });

//   MongoClient.connect(url, function(err, db) {
//     if (err) throw err;
//     var dbo = db.db("reportingDB");
//     dbo.createCollection("reports", function(err, res) {
//       if (err) throw err;
//       console.log("Collection created!");
//       db.close();
//     });
//   });

const reportsRoutes = require('./api/reports');
app.use('/reports', reportsRoutes);
app.use(myParser.urlencoded({extended : true}));

//app.get('/', (req, res) => res.send('Hello World!'));

module.exports = app;
