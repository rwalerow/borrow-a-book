import { Input, Component, ElementRef } from 'angular2/core';
import { ValidationError } from '../utils/validationUtils';

@Component({
    selector: 'my-app',
    templateUrl: '/assets/templates/first.html'
})
export default class AppComponent {

  validationErrors: Set<ValidationError> = new Set<ValidationError>();


  constructor(elementRef: ElementRef) {

    let jsObject = JSON.parse(elementRef.nativeElement.getAttribute('lista'));
    this.constructValidationErrors(jsObject);
  }

  private constructValidationErrors(jsonObject: any) {

      for (let o of jsonObject){
        this.validationErrors.add(new ValidationError().deserialize(o));
      }
  }

  clicked(event) {
    this.validationErrors.forEach(a => console.log(a));
  }
}
