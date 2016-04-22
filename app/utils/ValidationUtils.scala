package utils

import scalaz._
import Scalaz._

/**
  * Created by robert on 18.04.16.
  */
object ValidationUtils {

	case class ValidationError(field: String, errorCode: String)

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
