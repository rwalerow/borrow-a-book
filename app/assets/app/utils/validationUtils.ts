import { Serializable } from './generalInterfaces';
import { Control } from 'angular2/common';

export class Validations {
	static validateMinimalLength(length: number) {
		return (control: Control) => {
			if (control.value.length <= length) {
				return {loginToShort: true};
			}
		};
	}

	static validateEmailForm(control: Control) {
		let EMAIL_REGEXP = /^[a-z0-9!#$%&'*+\/=?^_`{|}~.-]+@[a-z0-9]([a-z0-9-]*[a-z0-9])?(\.[a-z0-9]([a-z0-9-]*[a-z0-9])?)*$/i;

		if (control.value !== '' && (control.value.length <= 5 || !EMAIL_REGEXP.test(control.value))) {
			return { incorrectEmailFormat: true };
		}
	}
}

export class ValidationError implements Serializable<ValidationError> {

	field: string;
	errorMessage: string;

    	constructor(field: string = '', error: string = '') {
		this.field = field;
		this.errorMessage = error;
	}

	deserialize(input): ValidationError {

		this.field = input.field;
		this.errorMessage = input.errorCode;

		return this;
	}

	addErrorMessage(error: string) {
		if (this.errorMessage === '') {
			this.errorMessage = error;
		} else {
			this.errorMessage += ', ' + error;
		}
	}
}
