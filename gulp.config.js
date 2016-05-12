module.exports = function() {
	var config = {
		allTs: './app/assets/app/**/*.ts',
		tsOutputPath: './public/javascripts/app/',
		tsMappings: [
//			"node_modules/angular2/core.d.ts",
			"node_modules/@angular/core/index.ts",
//			"node_modules/angular2/common.d.ts",
			"node_modules/@angular/common/index.d.ts",
//			"node_modules/angular2/http.d.ts",
			"node_modules/@angular/http/index.d.ts",
			"node_modules/angular2/router.d.ts",
			"node_modules/rxjs/Rx.d.ts",
//			"node_modules/angular2/typings/es6-collections/es6-collections.d.ts",
			"typings/es6-collections/es6-collections.d.ts",
//			"node_modules/angular2/typings/es6-promise/es6-promise.d.ts",
			"typings/es6-promise/es6-promise.d.ts",
//			"node_modules/@angular/"
			"node_modules/angular2/src/facede/collection.d.ts"
		],
		sassSourcePath: './app/assets/scss/**/*.scss',
		sassOutputPath: './public/stylesheets'
	};

	return config;
}