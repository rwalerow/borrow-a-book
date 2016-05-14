import { bootstrap } from 'angular2/platform/browser';
import RegistrationForm from '../users/registrationForm.component';
import { HTTP_PROVIDERS } from 'angular2/http';
import { ValidationService } from '../../service/validation.service';


bootstrap(RegistrationForm, [HTTP_PROVIDERS, ValidationService]);
