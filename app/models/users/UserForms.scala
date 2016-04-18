package models.users

import com.google.inject.Inject
import com.google.inject.Singleton
import models.users.daos.UserDao
import models.users.services.UserService
import play.api.data.Form
import play.api.data.Forms.{mapping, nonEmptyText}
import play.api.libs.json.{JsPath, Writes}

import scala.concurrent.{ExecutionContext, Future}
import scalaz._
import Scalaz._
import play.api.libs.json._
import play.api.libs.functional.syntax._

import ExecutionContext.Implicits.global

/**
  * Created by robert on 30.03.16.
  */

object UserForms {

	val login_filed_name = "userName"
	val email_filed_name = "email"
	val password_filed_name = "password"
	val password_confirmation_filed_name = "password_confir"

	val registration_form_error = "form"

	val login_minimal_length = 4
	val password_minimal_length = 5
	val email_regexp = """\b[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*\b""".r

	val registrationform = Form(
		mapping(
			login_filed_name -> nonEmptyText,
			email_filed_name -> nonEmptyText,
			password_filed_name -> nonEmptyText,
			password_confirmation_filed_name -> nonEmptyText
		)(RegistrationForm.apply)(RegistrationForm.unapply)
	)

	implicit val validationErrorWriter: Writes[ValidationError] = (
		(JsPath \ "field").write[String] and
		(JsPath \ "errorCode").write[String]
	)(unlift(ValidationError.unapply))
}

case class RegistrationForm(login: String
	,email: String
	,password: String
	,passwordConfirmation: String)

case class ValidationError(field: String, errorCode: String)

@Singleton
class RegistrationFormValidation @Inject()(userService: UserService) {

	def validate(registrationForm: RegistrationForm): Future[ValidationNel[ValidationError, RegistrationForm]] = {
		for {
			standard <- Future(validateStandard(registrationForm))
			async <- isLoginUnique(registrationForm.login)
		} yield (standard |@| async){(_,_) => registrationForm }
	}

	def validateStandard(registrationForm: RegistrationForm): ValidationNel[ValidationError, RegistrationForm] = {
		(
			validateNonBlankFields(registrationForm) |@|
			validateFieldMinimalLength(registrationForm) |@|
			isEmailValidForm(registrationForm.email) |@|
			isPasswordTheSame(registrationForm.password, registrationForm.passwordConfirmation)
		){	(_, _, _, _) => registrationForm }
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
		){ (_, _) => registrationForm }
	}

	def isValidLength(value: String, fieldName: String, minimalLength: Int): ValidationNel[ValidationError, String] = {
		if(value.length > 0 && value.length < minimalLength)
			ValidationError(fieldName, "validation.error.to.short").failureNel
		else
			value.successNel
	}

	def isPasswordTheSame(password: String, passwordConfirmation: String): ValidationNel[ValidationError, String] = {
		if(!password.equals(passwordConfirmation))
			ValidationError(UserForms.registration_form_error, "validation.error.passwords.are.different").failureNel
		else
			password.successNel
	}

	def isEmailValidForm(email: String) : ValidationNel[ValidationError, String] = {
		if(UserForms.email_regexp.unapplySeq(email).isDefined)
			email.successNel
		else
			ValidationError(UserForms.email_filed_name, "validation.error.invalid.email").failureNel
	}

	def isLoginUnique(login: String): Future[ValidationNel[ValidationError, String]] = {
		userService.countByName(login)
			.map(result => result > 0)
			.map(gtZero => {
				if(gtZero)
					login.successNel
				else
					ValidationError(UserForms.login_filed_name, "validation.error.login.already.exists").failureNel
			})
	}
}