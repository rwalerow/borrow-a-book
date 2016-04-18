package models.users.daos.validation

import com.google.inject.{Inject, Singleton}
import models.users.RegistrationForm
import models.users.daos.UserDao
import play.api.data.validation.Constraint

import scala.concurrent.Future

/**
  * Created by robert on 03.04.16.
  */
@Singleton
class UserValidation @Inject() (val userDao: UserDao){

//	def isNameUnique(registerForm: RegistrationForm) : Future[] = {
	//
	//	}
}
