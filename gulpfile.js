const gulp = require('gulp');
const del = require('del');
const ts = require('gulp-typescript');
const tscConfig = require('./tsconfig.json');


// clean the contents of the distribution directory
gulp.task('clean', function () {
  return del('dist/**/*');
});

// TypeScript compile
gulp.task('compile', ['clean'], function () {
  return gulp
    .src('app/assets/app/**/*.ts')
    .pipe(typescript(tscConfig))
    .pipe(gulp.dest('public/javascripts/app'));
});

gulp.task('libs', function () {
    return gulp.src([
          "node_modules/angular2/bundles/angular2.dev.js",
          "node_modules/angular2/bundles/angular2-polyfills.min.js",
          "node_modules/traceur/bin/traceur-runtime.js",
          "node_modules/es6-module-loader/dist/es6-module-loader.js",
          "node_modules/systemjs/dist/system.js",
          "node_modules/es6-shim/es6-shim.js",
          "node_modules/rxjs/Rx.js"
        ])
      .pipe(gulp.dest('public/javascripts/lib'));
});


gulp.task('ts', function(done) {
  var tsResult = gulp.src([
      "node_modules/angular2/core.d.ts",
      "node_modules/angular2/common.d.ts",
      "node_modules/angular2/http.d.ts",
      "node_modules/angular2/router.d.ts",
      "node_modules/rxjs/Rx.d.ts",
      "node_modules/angular2/typings/es6-collections/es6-collections.d.ts",
      "node_modules/angular2/typings/es6-promise/es6-promise.d.ts",
      "app/assets/app/**/*.ts"
    ])
    .pipe(ts(tscConfig), undefined, ts.reporter.fullReporter());
  return tsResult.js.pipe(gulp.dest('public/javascripts'));
});

gulp.task('front-dev', ['libs', 'ts'])
gulp.task('build', ['compile']);
gulp.task('default', ['build']);