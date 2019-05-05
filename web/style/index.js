const fs = require('fs');
const { execSync } = require('child_process');
const chalk = require('chalk');

const from = `${__dirname}/app.css`;
const toDir = `${__dirname}/../target`
const to = `${toDir}/style.css`;

console.log(`${chalk.cyan('core/style 1/2')} Verifying dependencies...`)
execSync('yarn');

console.log(`${chalk.cyan('core/style 2/2')} Running postcss...`)
fs.readFile(from, (err, css) => {
  require('postcss')([
    require('postcss-import'),
    require('tailwindcss')(`${__dirname}/tailwind.js`),
    require('autoprefixer'),
    require('cssnano')
  ])
    .process(css, { from, to })
    .then(result => {
      fs.mkdirSync(toDir, { recursive: true });
      fs.writeFile(to, result.css, () => true);
    })
})