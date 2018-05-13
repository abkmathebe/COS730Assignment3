const express = require('express');
const router = express.Router();
const MongoClient = require('mongodb');
const ObjectId = require('mongodb').ObjectId; 

var url = "mongodb://localhost:27017/reportingDB";
 var myParser = require("body-parser");
//var app = express();
/*
    This service should return a list of available benchmarks saved in the database
*/
router.use(myParser.urlencoded({extended : true}));
router.post('/getBenchmarksById', (req, res, next) => {

    console.log(req.body.id);
  

    MongoClient.connect(url, function(err, db) {
        if (err) throw err;
        var dbo = db.db("reportingDB");
      //  var query = { taskID: 3 };
  
      
        dbo.collection("reports").find({ _id : new ObjectId(req.body.id) } ).toArray(function(err, result) {
            if (err) throw err;
            console.log(result);
            res.status(200).json({
                data: result
            });
            db.close();
          });
        });


      
});

router.get('/getAllBenchmarks', (req, res, next) => {

    console.log(req.body);
    var toRet = {};

    MongoClient.connect(url, function(err, db) {
        if (err) throw err;
        var dbo = db.db("reportingDB");
      //  var query = { taskID: 3 };
  
      
        dbo.collection("reports").find().toArray(function(err, result) {
            if (err) throw err;
            console.log(result);
            toRet = result;
            res.status(200).json({
                data: result
            });
            db.close();
          });
        });

        //res.result = toRet;
     
});


/*
    This service should return a benchmarks by id saved in the database
*/
router.get('/benchmark', (req, res, next) => {

    res.status(200).json({
        message: "Handling get requests to /reports"
    });
});

/*
    This service should save summerised benchmark data from executions team together with the generated graphical images to the node server.
*/
router.post('/saveBenchmark', (req, res, next) => {

    res.status(200).json({
        message: "Handling post requests to /reports"
    });
});

/*
    Exaclty the same as the above service but works with bulk benchmarks
*/
router.post('/saveBenchmarkBulk', (req, res, next) => {

    res.status(200).json({
        message: "Handling post requests to /reports"
    });
});


/*
    This service gets the benchmark raw data from execution team
*/
router.get('/getBenchmark', (req, res, next) => {

    MongoClient.connect(url, function(err, db) {
        if (err) throw err;
        var dbo = db.db("reportingDB");

        var myobj = {};
        myobj.result = [];

        var task = {};
        task.taskID = 3;
        task.dispatcher = "Kudzai";
        task.result = [{
            "measurement":"CPU-Usage",
            "values":[
                {"Timestamp" : "20;00:01", "value":"45"},
                {"Timestamp" : "20;00:02", "value":"42"},
                {"Timestamp" : "20;00:03", "value":"56"},
                {"Timestamp" : "20;00:04", "value":"54"}
            ]
        },
        {
        "measurement":"Memory-Usage",
        "values":[
            {"Timestamp" : "20;00:01", "value":"45234234"},
            {"Timestamp" : "20;00:02", "value":"4223423424"},
            {"Timestamp" : "20;00:03", "value":"566666666"},
            {"Timestamp" : "20;00:04", "value":"5222342344"}
        ]
    }
    ];
    myobj.result.push(task);

        dbo.collection("reports").insertOne(myobj, function(err, res) {
          if (err) throw err;
          console.log("1 document inserted");
          db.close();
        });
    });


    res.status(200).json({
        message: "succesfully added records to db"
    });
});

module.exports = router;