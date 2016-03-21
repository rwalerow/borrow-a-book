module.exports = function() {
	var config = {
		allTs: './app/assets/app/**/*.ts',
		tsOutputPath: './public/javascripts/app/',
		tsMappings: [
			"node_modules/angular2/core.d.ts",
			"node_modules/angular2/common.d.ts",
			"node_modules/angular2/http.d.ts",
			"node_modules/angular2/router.d.ts",
			"node_modules/rxjs/Rx.d.ts",
			"node_modules/angular2/typings/es6-collections/es6-collections.d.ts",
			"node_modules/angular2/typings/es6-promise/es6-promise.d.ts"
		]
	};

	return config;
}