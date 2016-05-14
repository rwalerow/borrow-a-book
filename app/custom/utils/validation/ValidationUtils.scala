package custom.utils.validation

import play.api.libs.json.{JsPath, Writes}
import play.api.libs.json._
import play.api.libs.functional.syntax._

import scalaz.Scalaz._
import scalaz._

/**
  * Created by robert on 18.04.16.
  */
object ValidationUtils {

	case class ValidUniqueResponse(unique: Boolean)

	case class ValidationError(field: String, errorCode: String)

	implicit val respWrite: Writes[ValidUniqueResponse] =
		( JsPath \ "notUnique").write[Boolean].contramap { (valid: ValidUniqueResponse) => valid.unique }

	def isStringNonEmpty(fieldValue: String, filedName: String): ValidationNel[ValidationError, String] = {
		if(fieldValue.isEmpty)
			ValidationError(filedName, "validation.error.blank").failureNel
		else
			fieldValue.successNel
	}

	def isValidLength(value: String, fieldName: String, minimalLength: Int): ValidationNel[ValidationError, String] = {
		if(value.length > 0 && value.length < minimalLength)
			ValidationError(fieldName, "validation.error.to.short").failureNel
		else
			value.successNel
	}

}
