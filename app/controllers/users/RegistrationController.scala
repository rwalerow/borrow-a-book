package controllers.users

import com.google.inject.Inject
import custom.utils.validation.ValidationUtils.{ValidUniqueResponse, ValidationError, respWrite}
import models.users.services.UserService
import models.users.{RegistrationForm, RegistrationFormValidation, UserForms}
import play.api.Logger
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.libs.json.{Json, Writes}
import play.api.mvc._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
//import utils.ApplicationImplicits.defaultLang

/**
  * Created by robert on 23.03.16.
  */
class RegistrationController @Inject() (
				val messagesApi: MessagesApi,
				val registrationFormValidator: RegistrationFormValidation,
				val userService: UserService)
	extends Controller with I18nSupport {

	def get = Action {
		Logger.info("Hello robert")
		Ok(views.html.users.registration(model = RegistrationForm(login = "robcio")))
	}

	def post = Action.async { implicit request =>

		import scala.concurrent.ExecutionContext.Implicits.global

		UserForms.registrationform.bindFromRequest.fold(
			form => {
				val regForm = form.value.getOrElse(RegistrationForm())
				val validationerrors = form.errors
				    .map(error => ValidationError(error.key, error.message))
					.toList
				Future.successful(Ok(views.html.users.registration(model = regForm, error = validationerrors)))
			},
			data => {
				registrationFormValidator.validate(data).map( validationResult =>
					validationResult.fold(
						error => {
							Ok(views.html.users.registration(model = data, error = error.list))
						},
						result => Redirect(controllers.routes.Application.index)
					)
				);
			}
		)
	}

	def validateLoginUnique = Action.async { implicit request =>
		isUnique(request, "login", userService.isNameUnique)
	}

	def validateEmailUnique = Action.async { implicit request =>
		isUnique(request, "email", userService.isEmailUnique)
	}

	private def isUnique(request: Request[AnyContent], field: String, isUniqueFunction: String => Future[Boolean]): Future[Result] = {
		request.queryString.get(field)
			.flatMap(_.headOption)
			.map(isUniqueFunction(_))
			.getOrElse(Future.successful(false))
			.map(valid => Ok(Json.toJson(ValidUniqueResponse(!valid))))
	}
}
