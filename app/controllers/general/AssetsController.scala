package controllers.general

import java.io.File

import play.api.mvc.{Action, Controller}

/**
  * Created by robert on 23.03.16.
  */
class AssetsController extends Controller {

	def serveNodeFiles(file: String) = Action {
		Ok.sendFile(content = new File("node_modules/" + file), inline = true)
	}
}
