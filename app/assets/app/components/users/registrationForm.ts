import {Component} from 'angular2/core';
import {UserRegistration} from './userRegistrationModel';

@Component({
  selector: 'registration-form',
  templateUrl: '/assets/templates/registrationForm.html'
})
export default class RegistrationForm {

  model = new UserRegistration('', '', '', '');
  submitted = false;

  onSubmit() {
    this.submitted = true;
  }

  notEmpty(value) {
    if (value) {
      return true;
    }
    return false;
  }
}
