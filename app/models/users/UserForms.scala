package models.users

import play.api.data.Form
import play.api.data.Forms.{mapping, nonEmptyText}

import scala.concurrent.Future
import scalaz._
import Scalaz._
/**
  * Created by robert on 30.03.16.
  */

object UserForms {

	val login_filed_name = "userName"
	val email_filed_name = "email"
	val password_filed_name = "password"
	val password_confirmation_filed_name = "password_confir"

	val login_minimal_length = 4
	val password_minimal_length = 5

	val registrationform = Form(
		mapping(
			login_filed_name -> nonEmptyText,
			email_filed_name -> nonEmptyText,
			password_filed_name -> nonEmptyText,
			password_confirmation_filed_name -> nonEmptyText
		)(RegistrationForm.apply)(RegistrationForm.unapply)
	)
}

case class RegistrationForm(login: String
							, email: String
							, password: String
							, passwordConfirmation: String)

case class ValidationError(field: String, errorCode: String)

object RegistrationForm {

	def validate(registrationForm: RegistrationForm): ValidationNel[ValidationError, RegistrationForm] = {
		(
			validateNonBlankFields(registrationForm) |@|
			validateFieldMinimalLength(registrationForm)
		) {
			(_, _) => registrationForm
		}
	}

	def validateNonBlankFields(registrationForm: RegistrationForm): ValidationNel[ValidationError, RegistrationForm] = {
		(
			isStringNonEmpty(registrationForm.login, UserForms.login_filed_name) |@|
			isStringNonEmpty(registrationForm.password, UserForms.password_filed_name) |@|
			isStringNonEmpty(registrationForm.email, UserForms.email_filed_name) |@|
			isStringNonEmpty(registrationForm.passwordConfirmation, UserForms.password_confirmation_filed_name)
		){ (_, _, _, _) => registrationForm }
	}

	def isStringNonEmpty(fieldValue: String, filedName: String): ValidationNel[ValidationError, String] = {
		if(fieldValue.isEmpty)
			ValidationError(filedName, "validation.error.blank").failureNel
		else
			fieldValue.successNel
	}

	def validateFieldMinimalLength(registrationForm: RegistrationForm): ValidationNel[ValidationError, RegistrationForm] = {
		(
			isValidLength(registrationForm.login, UserForms.login_filed_name, UserForms.login_minimal_length) |@|
			isValidLength(registrationForm.password, UserForms.password_filed_name, UserForms.password_minimal_length)
		) { (_, _) => registrationForm }
	}

	def isValidLength(value: String, fieldName: String, minimalLength: Int): ValidationNel[ValidationError, String] = {
		if(value.length > 0 && value.length < minimalLength)
			ValidationError(fieldName, "validation.error.to.short").failureNel
		else
			value.successNel

	}

	def isLoginUnique(login: String): Future[ValidationNel[String, String]] = {
		???
	}

}