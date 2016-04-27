package controllers

import com.google.inject.Inject
import play.api.mvc._
import play.api.i18n.I18nSupport
import play.api.i18n.MessagesApi

//import utils.ApplicationImplicits._

class Application @Inject() (val messagesApi: MessagesApi) extends Controller with I18nSupport {

	def index = Action {
		Ok(views.html.index("Your new application is ready."))
	}

	def materializeDemo = Action {
		Ok(views.html.materialize("a"))
	}
}
