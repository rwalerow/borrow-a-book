import { Component, ElementRef } from 'angular2/core';
import { UserRegistration } from './userRegistrationModel';
import { isBlank } from 'angular2/src/facade/lang';
import { ControlGroup, FormBuilder, Validators, FORM_DIRECTIVES, Control } from 'angular2/common';
import { ValidationError } from '../../utils/validationUtils';
import * as i from 'immutable';


@Component({
	selector: 'registration-form',
	templateUrl: '/assets/templates/registrationForm.html',
	directives: [FORM_DIRECTIVES],
	styles: ['.input-field { margin-bottom: 40px; }']
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
			'login': ['', Validators.compose([Validators.required, this.validateMinimalLength(3)])],
			'email': ['', Validators.compose([Validators.required, this.validateEmailForm])],
			'password': ['', Validators.compose([Validators.required, this.validateMinimalLength(3)])],
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

	/**
	*	Validation methods
	**/

	matchingPasswords(passwordKey: string, passwordConfirmationKey: string) {
		return (group: ControlGroup) => {
			let passwordInput = group.controls[passwordKey];
			let passwordConfirmationInput = group.controls[passwordConfirmationKey];
			if (passwordInput.value !== passwordConfirmationInput.value) {
				return passwordConfirmationInput.setErrors({notEquivalent: true});
			}
		};
	}

	validateMinimalLength(length: number) {
		return (control: Control) => {
			if (control.value.length <= length) {
				return {loginToShort: true};
			}
		};
	}

	validateLoginMinimalSize(control: Control) {
		if (control.value.length <= 3) {
			return {loginToShort: true};
		}
	}

	validateEmailForm(control: Control) {
		let EMAIL_REGEXP = /^[a-z0-9!#$%&'*+\/=?^_`{|}~.-]+@[a-z0-9]([a-z0-9-]*[a-z0-9])?(\.[a-z0-9]([a-z0-9-]*[a-z0-9])?)*$/i;

		if (control.value !== '' && (control.value.length <= 5 || !EMAIL_REGEXP.test(control.value))) {
			return { incorrectEmailFormat: true };
		}
	}

	/**
	*	Validation error presentation
	**/

	loginValidationErrors(): string {
		let result: Set<string> = new Set<string>();
		let loginControl = this.registrationForm.find('login');

		if (loginControl.hasError('required')) {
			result.add('required');
		}

		if (loginControl.hasError('loginToShort')) {
			result.add('to short');
		}

		return 'Login is' + i.List(result).join(' and ');
	}

	passwordValidationErrors(): string {
		let result: Set<string> = new Set<string>();
		let passwordControl = this.registrationForm.find('password');

		if (passwordControl.hasError('required')) {
			result.add('required');
		}

		if (passwordControl.hasError('loginToShort')) {
			result.add('to short');
		}

		return 'Password is ' + i.List(result).join(' and ');
	}

	passwordConfirmationValidationErrors(): string {
		let result: Set<string> = new Set<string>();
		let passConfControl = this.registrationForm.find('passwordConfirm');

		if (passConfControl.hasError('notEquivalent')) {
			result.add('is not equal to password');
		}

		if (passConfControl.hasError('required')) {
			result.add('is required');
		}

		return 'Password confirmation ' + i.List(result).join(',');
	}

	emailValidationErrors(): string {
		let result: Set<string> = new Set<string>();
		let emailControl = this.registrationForm.find('email');

		if (emailControl.hasError('required')) {
			result.add('required');
		}

		if (emailControl.hasError('incorrectEmailFormat')) {
			result.add('in incorrct form');
		}

		return 'Email is ' + i.List(result).join(' and ');
	}

	private parseValidationErrors(jsonObject: any) {
		this.validationErrors = i.Set(jsonObject)
			.map(e => new ValidationError().deserialize(e))
			.toSet();
	}
}
