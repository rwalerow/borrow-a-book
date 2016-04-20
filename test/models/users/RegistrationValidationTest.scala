package models.users

import scala.concurrent.Await
import scala.concurrent.duration._

import akka.stream.Materializer
import org.scalatest.OptionValues
import org.typelevel.scalatest.ValidationMatchers
import utils.PlayDbSpec
import utils.ValidationUtils.ValidationError

/**
  * Created by robert on 18.04.16.
  */
class RegistrationValidationTest extends PlayDbSpec with OptionValues with ValidationMatchers {

	val validatior: RegistrationFormValidation = app.injector.instanceOf(classOf[RegistrationFormValidation])

	implicit lazy val materializer: Materializer = app.materializer

	"RegistrationForm" should {

		"be valid" in {
			val form = RegistrationForm("jkowalski", "kowalski@gmail.com", "password", "password")
			val validationResult = Await.result(validatior.validate(form), 3 second)

			validationResult.isSuccess mustEqual true
		}

		"must be ununique" in {
			val invalidForm = RegistrationForm("admin", "kowalski@gmail.com", "password", "password")
			val validationResult = Await.result(validatior.validate(invalidForm), 3 second)

			validationResult must haveFailure (ValidationError("login", "validation.error.login.already.exists"))
		}
	}
}
