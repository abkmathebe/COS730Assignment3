const http = require('http');
const app = require('./app');
const port = process.env.PORT || 3000;
const server = http.createServer();
//server.listen(port);

app.listen(3000, () => console.log('listening on port 3000!'))

