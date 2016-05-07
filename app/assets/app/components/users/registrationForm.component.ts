import { Component, ElementRef } from 'angular2/core';
import { UserRegistration } from './userRegistrationModel';
import { isBlank } from 'angular2/src/facade/lang';
import { ControlGroup, FormBuilder, Validators, FORM_DIRECTIVES, Control } from 'angular2/common';
import { ValidationError } from '../../utils/validationUtils';
import * as i from 'immutable';


@Component({
	selector: 'registration-form',
	templateUrl: '/assets/templates/registrationForm.html',
	directives: [FORM_DIRECTIVES]
})
export default class RegistrationForm {

	validationErrors: i.Set<ValidationError>;
	model: UserRegistration = new UserRegistration('', '', '', '');
	registrationForm: ControlGroup;
	submitted = false;

	constructor(elementRef: ElementRef, fb: FormBuilder) {
		let errorsFronAttribute = JSON.parse(elementRef.nativeElement.getAttribute('validation-errors'));
		this.parseValidationErrors(errorsFronAttribute);
		this.registrationForm = fb.group({
			'login': ['', Validators.compose([Validators.required, this.validateLoginMinimalSize])],
			'email': [''],
			'password': [''],
			'passwordConfirm': ['', Validators.required ]
		}, { validator: this.matchingPasswords('password', 'passwordConfirm')});
	}

	onSubmit() {
		this.submitted = true;
	}

	notEmpty(value) {
		if (value) {
			return true;
		}
		return false;
	}

	isFormValid(): boolean {
		return this.registrationForm.valid;
	}

	isFieldInvalid(fieldName: string) {
		return this.validationErrors
			.filter(e => e.field === fieldName)
			.size !== 0;
	}

	errorsForField(fieldName: string): string {
		return this.validationErrors
			.filter(e => e.field === fieldName)
			.map(e => e.errorMessage)
			.first();
		}

  // Validation methods

	matchingPasswords(passwordKey: string, passwordConfirmationKey: string) {
		return (group: ControlGroup) => {
			let passwordInput = group.controls[passwordKey];
			let passwordConfirmationInput = group.controls[passwordConfirmationKey];
			if (passwordInput.value !== passwordConfirmationInput.value) {
				return passwordConfirmationInput.setErrors({notEquivalent: true});
			}
		};
	}

	validateLoginMinimalSize(control: Control) {
		if (control.value.length <= 3) {
			return {loginToShort: true};
		}
	}

	loginValidationErrors(): string {
		let result: Set<string> = new Set<string>();
		let loginControl = this.registrationForm.find('login');

		if (loginControl.hasError('required')) {
			result.add('is required');
		}

		if (loginControl.hasError('loginToShort')) {
			result.add('to short');
		}

		return 'Login is' + i.List(result).join(' and ');
	}

	passwordConfirmationValidationErrors(): string {
		let result: Set<string> = new Set<string>();
		let passConfControl = this.registrationForm.find('passwordConfirm');

		console.log(passConfControl);

		if (passConfControl.hasError('notEquivalent')) {
			result.add('is not equal to password');
		}

		if (passConfControl.hasError('required')) {
			result.add('is required');
		}

		return 'Password confirmation ' + i.List(result).join(',');
	}

	private parseValidationErrors(jsonObject: any) {
		this.validationErrors = i.Set(jsonObject)
			.map(e => new ValidationError().deserialize(e))
			.toSet();
	}
}
