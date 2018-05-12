const express = require('express');
const app = express();
const mongo = require('mongodb');
const assert = require('assert');

var url = '';

const reportsRoutes = require('./api/reports');
app.use('/reports', reportsRoutes);

app.get('/', (req, res) => res.send('Hello World!'));

module.exports = app;
