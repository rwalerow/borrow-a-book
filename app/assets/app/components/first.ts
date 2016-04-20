import {Input, Component} from 'angular2/core';
import * as Collections from 'typescript-collections';

@Component({
    selector: 'my-app',
    templateUrl: 'assets/templates/first.html'
})
export default class AppComponent {
  @Input() example: string;
}

class Simple {
  key: string;
  value: string;
}
