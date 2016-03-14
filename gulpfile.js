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


gulp.task('build', ['compile']);
gulp.task('default', ['build']);