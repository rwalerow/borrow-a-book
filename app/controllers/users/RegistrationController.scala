package controllers.users

import com.google.inject.Inject
import custom.utils.validation.ValidationUtils.{respWrite, ValidUniqueResponse, ValidationError}
import models.users.services.UserService
import models.users.{RegistrationForm, RegistrationFormValidation, UserForms}
import play.api.Logger
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.libs.json.{Json, Writes}
import play.api.mvc.{Action, Controller}

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
		Ok(views.html.users.registration(List(ValidationError("userName", "wtf test invalid name"))))
	}

	def post = Action.async { implicit request =>

		import scala.concurrent.ExecutionContext.Implicits.global

		UserForms.registrationform.bindFromRequest.fold(
			form => {
				Logger.info(form.toString)
				Future.successful(Ok(views.html.users.registration()))
			},
			data => {
				Logger.info(data.toString)
				registrationFormValidator.validate(data).map( validationResult =>
					validationResult.fold(
						error => Ok(views.html.users.registration()),
						result => Ok(views.html.index("app"))
					)
				);
			}
		)
	}

	def validateLoginUnique = Action.async { implicit request =>
		val login = request.queryString.get("login").flatMap(_.headOption).getOrElse("admin")
		userService.isNameUnique(login).map {
			valid => Ok(Json.toJson(ValidUniqueResponse(valid)))
		}
	}
}
