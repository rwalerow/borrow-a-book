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
        'login': ['', Validators.compose([Validators.required, this.loginMinimalSize])]
      });
  }

  onSubmit() {
    this.submitted = true;
    console.log(this.registrationForm);
  }

  notEmpty(value) {
    if (value) {

      return true;
    }
    return false;
  }

  isFormValid(): boolean {
    console.log(this.registrationForm.valid);
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
  loginMinimalSize(control: Control) {
    console.log('control.value.size <= 3:');
    console.log(control.value.length);
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

    if (loginControl.hasError('loginToShort'))
    result.add(' to short');

    return 'Login is' + i.List(result).join(' and');
  }

  private parseValidationErrors(jsonObject: any) {
    this.validationErrors = i.Set(jsonObject)
      .map(e => new ValidationError().deserialize(e))
      .toSet();
  }
}
