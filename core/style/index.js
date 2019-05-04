const fs = require('fs')

const from = `${__dirname}/app.css`;
const toDir = `${__dirname}/../target/style`
const to = `${toDir}/app.css`;

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