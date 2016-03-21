var gulp = require('gulp');
var sourcemaps = require('gulp-sourcemaps');
var tsc = require('gulp-typescript');
var tslint = require('gulp-tslint');
var tsProject = tsc.createProject('tsconfig.json');
var config = require('./gulp.config')();

gulp.task('ts-lint', function() {
    return gulp.src(config.allTs)
        .pipe(tslint())
        .pipe(tslint.report('prose', {
            emitError: false
        }));
})

gulp.task('compile-ts', function() {
    var sourceTsFiles = [
        config.allTs
    ];

    var tsResult = gulp
        .src(sourceTsFiles)
        .pipe(sourcemaps.init())
        .pipe(tsc(tsProject));

    return tsResult.js
        .pipe(sourcemaps.write('.'))
        .pipe(gulp.dest(config.tsOutputPath));
});

gulp.task('libs-js', function () {
    return gulp.src([
          "node_modules/angular2/bundles/angular2.dev.js",
          "node_modules/angular2/bundles/angular2-polyfills.min.js",
          "node_modules/angular2/core.js",
          "node_modules/angular2/platform/browser.js",
          "node_modules/traceur/bin/traceur-runtime.js",
          "node_modules/es6-module-loader/dist/es6-module-loader.js",
          "node_modules/systemjs/dist/system.js",
          "node_modules/es6-shim/es6-shim.js",
          "node_modules/rxjs/Rx.js",
          "node_modules/typescript/lib/typescript.js",
          "node_modules/materialize-css/dist/js/materialize.js",
          "node_modules/requirejs/require.js"
        ])
      .pipe(gulp.dest('public/javascripts/lib'));
});

gulp.task('libs-css', function(){
	return gulp.src([
		"node_modules/materialize-css/dist/css/materialize.css"
	])
	.pipe(gulp.dest("public/stylesheets"));
});

