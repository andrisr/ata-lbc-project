const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const CopyPlugin = require("copy-webpack-plugin");

module.exports = {
  optimization: {
    usedExports: true
  },
  entry: {
    homePage: path.resolve(__dirname, 'src', 'pages', 'homePage.js')
  },
  output: {
    path: path.resolve(__dirname, 'dist'),
    filename: '[name].js',
  },
  devServer: {
    https: false,
    port: 8080,
    open: true,
    openPage: 'http://localhost:8080/home.html',
    // disableHostChecks, otherwise we get an error about headers and the page won't render
    disableHostCheck: true,
    contentBase: 'packaging_additional_published_artifacts',
    // overlay shows a full-screen overlay in the browser when there are compiler errors or warnings
    overlay: true,
    proxy:[
      {
        context: [
          '/rsvp'
        ],
        target: 'http://localhost:5001'
      }
    ]
  },
  plugins: [
    new HtmlWebpackPlugin({
      template: './src/home.html',
      filename: 'home.html',
      inject: false,
      minify: false
    }),
    new HtmlWebpackPlugin({
      template: './src/directions.html',
      filename: 'directions.html',
      inject: false,
      minify: false
    }),
    new HtmlWebpackPlugin({
      template: './src/bridalLogin.html',
      filename: 'bridalLogin.html',
      inject: false,
      minify: false
    }),
    new HtmlWebpackPlugin({
      template: './src/registry.html',
      filename: 'registry.html',
      inject: false,
      minify: false
    }),
    new CopyPlugin({
      patterns: [
        {
          from: path.resolve('src/css'),
          to: path.resolve("dist/css")
        },
        {
          from: path.resolve('src/images'),
          to: path.resolve("dist/images")
        }
      ]
    })
  ]
}
