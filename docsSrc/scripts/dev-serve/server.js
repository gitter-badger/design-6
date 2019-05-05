const express = require('express');
const path = require('path');

const port = 3000;

const app = express();
const root = path.resolve(__dirname + '/../../..');

app.use(express.static(`${root}/docsSrc/public`));

app.get('/web.css', (req, res) => {
  res.sendFile(`${root}/web/target/style.css`);
});

app.get('/app*.js', (req, res) => {
  res.sendFile(root
    + '/docsSrc/target/scala-2.12/scalajs-bundler/main/'
    + req.path.replace('app', 'docssrc-fastopt')
  );
});

app.get('/*', (req, res) => {
  res.sendFile(`${root}/docsSrc/public/index.html`);
});

app.listen(port, () => {
  console.log(`success Started at port ${port}!`)
});
