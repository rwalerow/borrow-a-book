package controllers

import play.api.mvc._

class Application extends Controller {

	def index = Action {
		Ok(views.html.index("Your new application is ready."))
	}

	def materializeDemo = Action {
		Ok(views.html.materialize("a"))
	}

	def registrationGet = Action {
		Ok(views.html.users.registration())
	}
}
