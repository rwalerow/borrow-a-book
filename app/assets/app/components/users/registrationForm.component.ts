import { Component, ElementRef, OnInit, AfterViewChecked } from 'angular2/core';
import { UserRegistration } from './userRegistrationModel';
import { isBlank } from 'angular2/src/facade/lang';
import { ControlGroup, FormBuilder, Validators, FORM_DIRECTIVES, Control } from 'angular2/common';
import { ValidationError, Validations } from '../../utils/validationUtils';
import { Http, Response } from 'angular2/http';
import { Observable } from 'rxjs/Observable';
import * as i from 'immutable';
import { ValidationService } from '../../service/validation.service';
import 'rxjs/Rx';


@Component({
	selector: 'registration-form',
	templateUrl: '/assets/templates/registrationForm.html',
	directives: [FORM_DIRECTIVES],
	// providers: [ValidationService],
	styles: ['.input-field { margin-bottom: 40px; }']
})
export default class RegistrationForm {

	validationErrors: i.Set<ValidationError>;
	model: UserRegistration = new UserRegistration('', '', '', '');
	registrationForm: ControlGroup;
	submitted = false;

	constructor(public validationService: ValidationService, elementRef: ElementRef, fb: FormBuilder) {
		
		let errorsFronAttribute = JSON.parse(elementRef.nativeElement.getAttribute('validation-errors'));
		this.parseValidationErrors(errorsFronAttribute);

		let initialModel = JSON.parse(elementRef.nativeElement.getAttribute('init-model'));

		console.log(initialModel);
		this.registrationForm = fb.group({
			'login': [initialModel.login, Validators.compose([Validators.required, Validators.minLength(3)]), this.validateUniqueLogin()],
			'email': [initialModel.email, Validators.compose([Validators.required, Validations.validateEmailForm]), this.validationEmailUnique()],
			'password': ['', Validators.compose([Validators.required, Validators.minLength(3)])],
			'passwordConfirm': ['', Validators.required ]
		}, { validator: this.matchingPasswords('password', 'passwordConfirm')});
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

	cleanFieldError(fieldName: string) {
		let toRemove: i.Set<ValidationError> = this.validationErrors
			.filter(error => error.field === fieldName)
			.toSet();

		this.validationErrors = this.validationErrors.subtract(toRemove);
	}

	matchingPasswords(passwordKey: string, passwordConfirmationKey: string) {
		return (group: ControlGroup) => {
			let passwordInput = group.controls[passwordKey];
			let passwordConfirmationInput = group.controls[passwordConfirmationKey];
			if (passwordInput.value !== passwordConfirmationInput.value) {
				return passwordConfirmationInput.setErrors({notEquivalent: true});
			}
		};
	}

	validateUniqueLogin() {
		return (control: Control) => {
			let login = control.value;
			return this.validationService.isLoginUnique(login);
		};
	}

	validationEmailUnique() {
		return (control: Control) => {
			let email = control.value;
			return this.validationService.isEmailUnique(email);
		};
	}
	/**
	*	Validation error presentation
	**/

	loginValidationErrors(): string {
		// Initial form errors
		if (this.isFieldInvalid('userName')) {
			return 'Login is ' + this.initialErrors('userName');
		}

		let result: Set<string> = new Set<string>();
		let loginControl = this.registrationForm.find('login');

		if (loginControl.hasError('required')) {
			result.add('required');
		}

		if (loginControl.hasError('minlength')) {
			result.add('to short');
		}

		if (loginControl.hasError('notUnique')) {
			result.add('not unique');
		}

		return 'Login is ' + i.List(result).join(' and ');
	}

	initialErrors(fieldName: string): string {
		return this.validationErrors
			.filter(element => element.field === fieldName)
			.map(element => element.errorMessage)
			.join(', ');
	}

	passwordValidationErrors(): string {
		// Initial form errors
		if (this.isFieldInvalid('password')) {
			return 'Password is ' + this.initialErrors('password');
		}

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
		// Initial form errors
		if (this.isFieldInvalid('password_confirmation')) {
			return 'Password confirmation ' + this.initialErrors('password_confirmation');
		}

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
		// Initial form errors
		if (this.isFieldInvalid('email')) {
			return 'Email is ' + this.initialErrors('email');
		}

		let result: Set<string> = new Set<string>();
		let emailControl = this.registrationForm.find('email');

		if (emailControl.hasError('required')) {
			result.add('required');
		}

		if (emailControl.hasError('incorrectEmailFormat')) {
			result.add('in incorrct form');
		}

		if (emailControl.hasError('notUnique')) {
			result.add('not unique');
		}

		return 'Email is ' + i.List(result).join(' and ');
	}

	private parseValidationErrors(jsonObject: any) {
		this.validationErrors = i.Set(jsonObject)
			.map(e => new ValidationError().deserialize(e))
			.toSet();
	}
}
