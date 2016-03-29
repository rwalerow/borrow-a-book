package controllers.users

import com.google.inject.Inject
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc.{Action, Controller}

import utils.ApplicationImplicits.defaultLang

/**
  * Created by robert on 23.03.16.
  */
class RegistrationController @Inject() (val messagesApi: MessagesApi) extends Controller with I18nSupport {

	def get = Action {
		Ok(views.html.users.registration())
	}
}
