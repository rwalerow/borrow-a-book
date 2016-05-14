import { Injectable } from 'angular2/core';
import { Observable } from 'rxjs/Observable';
import { Http, Response } from 'angular2/http';
import 'rxjs/Rx';

@Injectable()
export class ValidationService {
	constructor(public http: Http) {}

	isLoginUnique(login: string) {
		let url = 'http://localhost:9000/validation/users/isLoginUnique.html?login=' + login;
		return this.http.get(url)
			.map(r => r.json())
			.filter(r => r.notUnique)
			.toPromise();
	}

	isEmailUnique(email: string) {
		let url = 'http://localhost:9000/validation/users/isEmailUnique.html?email=' + email;
		return this.http.get(url)
			.map(r => r.json())
			.filter(r => r.notUnique)
			.toPromise();
	}
}
