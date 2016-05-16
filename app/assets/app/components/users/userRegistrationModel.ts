import { Serializable } from '../../utils/generalInterfaces';

export class UserRegistration implements Serializable<UserRegistration> {

	constructor (
		public login: string,
		public email: string,
		public password: string,
		public passwordConfirmation: string
	) {}

	deserialize(input): UserRegistration {
		this.login = input.login;
		this.email = input.email;

		return this;
	}
}
