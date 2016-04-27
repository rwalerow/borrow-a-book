import { Serializable } from './generalInterfaces';

export class ValidationError implements Serializable<ValidationError> {

    field: string;
    errorMessage: string;

    deserialize(input): ValidationError {
      this.field = input.field;
      this.errorMessage = input.errorMessage;

      return this;
    }
}
