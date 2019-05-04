const express = require('express');
const path = require('path');
const { execSync } = require('child_process');
const chalk = require('chalk');

console.log(chalk.magenta('docs-dev-serve 1/2') + ' verify deps...')
execSync('yarn');

console.log(chalk.magenta('docs-dev-serve 2/2') + ' start local server...')
const app = express();

const root = path.resolve(__dirname + '/../../..');

app.use(express.static(`${root}/docsSrc/public`));

app.get('/core-style.css', (req, res) => {
  res.sendFile(`${root}/core/target/style.css`);
});

app.get('/app*.js', (req, res) => {
  res.sendFile(root
    + '/docsSrc/target/scala-2.12/scalajs-bundler/main/'
    + req.path.replace('app', 'docssrc-fastopt')
  )
});

app.get('/*', (req, res) => {
  res.sendFile(`${root}/docsSrc/public/index.html`);
});

app.listen(3000, () => console.log('Port 3000!'));
