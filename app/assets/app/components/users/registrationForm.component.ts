import {Component, ElementRef} from 'angular2/core';
import {UserRegistration } from './userRegistrationModel';
import { ValidationError } from '../../utils/validationUtils';


@Component({
  selector: 'registration-form',
  templateUrl: '/assets/templates/registrationForm.html'
})
export default class RegistrationForm {

  validationErrors: Set<ValidationError> = new Set<ValidationError>();
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
    let response: boolean = false;
    this.validationErrors.forEach ( error => {
      if ( error.field === fieldName ) {
        response = true;
      };
    });
    return response;
  }

  errorsForField(fieldName: string) {
    let result: string = '';

    this.validationErrors.forEach ( e => {
      if (e.field === fieldName) {
        result = e.errorMessage;
      }
    });
    return result;
  }

  logErrors() {
    console.log(this.isFieldInvalid('userName'));

    this.validationErrors.forEach(e => {
      console.log(e);
    });
  }

  private parseValidationErrors(jsonObject: any) {
    for (let error of jsonObject) {
      this.validationErrors.add(new ValidationError().deserialize(error));
    }
  }
}
