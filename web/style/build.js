const fs = require('fs');
const path = require('path');

const from = `${__dirname}/index.css`;
const toDir = path.resolve(`${__dirname}/../target`);
const to = `${toDir}/style.css`;

if (fs.existsSync(to)) { fs.unlinkSync(to); }

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

const pathFromRoot = path.relative(`${__dirname}/../..`, to)
console.log(`success Built at <root>/${pathFromRoot}!`)