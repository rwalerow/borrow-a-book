package models.users

import scala.concurrent.Await
import scala.concurrent.duration._
import akka.stream.Materializer
import org.scalatest.OptionValues
import org.typelevel.scalatest.ValidationMatchers
import utils.PlayDbSpec
import custom.utils.validation.ValidationUtils.ValidationError

/**
  * Created by robert on 18.04.16.
  */
class RegistrationValidationTest extends PlayDbSpec with OptionValues with ValidationMatchers {

	val validatior: RegistrationFormValidation = app.injector.instanceOf(classOf[RegistrationFormValidation])

	implicit lazy val materializer: Materializer = app.materializer

	"RegistrationForm" should {

		"pass valid form" in {
			val form = RegistrationForm("jkowalski", "kowalski@gmail.com", "password", "password")
			val validationResult = Await.result(validatior.validate(form), 2 second)

			validationResult.isSuccess mustEqual true
		}

		"detect not unique login" in {
			val invalidForm = RegistrationForm("admin", "kowalski@gmail.com", "password", "password")
			val validationResult = Await.result(validatior.validate(invalidForm), 1 second)

			validationResult.leftSide must haveFailure (ValidationError("userName", "validation.error.login.already.exists"))
		}

		"detect not unique email" in {
			val invalidForm = RegistrationForm("admin2", "admin@home.pl", "password", "password")
			val validationResult = Await.result(validatior.validate(invalidForm), 1 second)

			validationResult.leftSide must haveFailure (ValidationError("email", "validation.error.email.already.exists"))
		}

		"detect invalid email form" in {
			val invalidForm = RegistrationForm("admin2", "admin@home", "password", "password")
			val validationResult = Await.result(validatior.validate(invalidForm), 1 second)

			validationResult.leftSide must haveFailure (ValidationError("email", "validation.error.invalid.email"))
		}
	}
}
