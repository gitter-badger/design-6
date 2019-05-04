const express = require('express');
const path = require('path');
const app = express();

app.get('/', function (req, res) {
  res.sendFile(path.join(__dirname, './index.html'));
});

app.use(express.static(path.join(__dirname, '../../public')));

app.get('/*', function (req, res) {
  res.sendFile(path.join(__dirname, './index.html'));
});

app.listen(3000, () => console.log('Port 3000!'));
