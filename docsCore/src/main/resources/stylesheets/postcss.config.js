module.exports = {
    plugins: [
        require('tailwindcss')('./core/src/main/resources/stylesheets/tailwind.js'),
        require('@csstools/postcss-sass'),
        require('autoprefixer'),
        require('cssnano')
    ]
};