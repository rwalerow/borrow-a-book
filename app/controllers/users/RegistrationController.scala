package controllers.users

import play.api.mvc.{Action, Controller}

/**
  * Created by robert on 23.03.16.
  */
class RegistrationController extends Controller {

	def get = Action {
		Ok(views.html.users.registration())
	}
}
