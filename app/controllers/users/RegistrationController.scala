package controllers.users

import com.google.inject.Inject
import models.users.UserForms
import play.api.Logger
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc.{Action, Controller}
//import utils.ApplicationImplicits.defaultLang

/**
  * Created by robert on 23.03.16.
  */
class RegistrationController @Inject() (val messagesApi: MessagesApi) extends Controller with I18nSupport {

	def get = Action {
		Logger.info("Hello robert")
		Ok(views.html.users.registration())
	}

	def post = Action { implicit request =>
		UserForms.registrationform.bindFromRequest.fold(
			form => {
				Logger.info(form.toString)
				Ok(views.html.users.registration())
			},
			data => {
				Logger.info(data.toString)
				Ok(views.html.users.registration())
			}
		)
	}
}
