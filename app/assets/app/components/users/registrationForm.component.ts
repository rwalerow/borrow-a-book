import { Component, ElementRef } from 'angular2/core';
import { UserRegistration } from './userRegistrationModel';
import { ValidationError } from '../../utils/validationUtils';
import * as i from 'immutable';


@Component({
  selector: 'registration-form',
  templateUrl: '/assets/templates/registrationForm.html'
})
export default class RegistrationForm {

  validationErrors: i.Set<ValidationError>;
  model = new UserRegistration('', '', '', '');
  submitted = false;

  constructor(elementRef: ElementRef) {
      let errorsFronAttribute = JSON.parse(elementRef.nativeElement.getAttribute('validation-errors'));
      this.parseValidationErrors(errorsFronAttribute);
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

  logErrors() {
    console.log(this.isFieldInvalid('userName'));

    this.validationErrors.forEach(e => {
      console.log(e);
    });
  }

  private parseValidationErrors(jsonObject: any) {
    this.validationErrors = i.Set(jsonObject)
      .map(e => new ValidationError().deserialize(e))
      .toSet();
  }
}
