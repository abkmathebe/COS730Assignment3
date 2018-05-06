const express = require('express');
const router = express.Router();

/*
    This service should return a list of available benchmarks saved in the database
*/
router.get('/benchmarks', (req, res, next) => {

        res.status(200).json({
            message: "Handling get requests to /reports"
        });
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

module.exports = router;