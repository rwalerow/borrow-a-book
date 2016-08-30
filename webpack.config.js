"use strict";

var path = require('path');
var webpack = require('webpack');

module.exports = {
    context: __dirname,
    devtool: 'eval',
    entry: 'app/main-entry.js',
    output: {
        publicPath: 'static/',
        filename: 'main-bundle.js'
    },
    resolve: {
        extensions: ['', '.js', '.jsx']
    },
    module: {
        loaders: [
            {
                test: /\.jsx$/,
                loader: 'babel',
                query: {
                    presets: ['es2015', 'react']
                }
            },
            {
                test: /\.js$/,
                loader: 'babel',
                query: {
                    presets: ['es2015']
                }
            }
        ]
    }
};