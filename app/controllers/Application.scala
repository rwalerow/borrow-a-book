package controllers

import java.io.File

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

	def node(file: String) = Action {
		Ok.sendFile(content = new File("node_modules/" + file), inline = true)
	}
}
