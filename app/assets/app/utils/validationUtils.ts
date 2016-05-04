import { Serializable } from './generalInterfaces';

export class ValidationError implements Serializable<ValidationError> {

    field: string;
    errorMessage: string;

    constructor(field: string = '', error: string = '') {
      this.field = field;
      this.errorMessage = error;
    }

    deserialize(input): ValidationError {

      this.field = input.field;
      this.errorMessage = input.errorCode;

      return this;
    }

    addErrorMessage(error: string) {
      if (this.errorMessage === '') {
        this.errorMessage = error;
      } else {
        this.errorMessage += ', ' + error;
      }
    }
}
