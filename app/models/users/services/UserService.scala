package models.users.services

import com.google.inject.{Inject, Singleton}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.driver.JdbcProfile
import models.users.daos.UsersDefinitions._
import slick.driver.PostgresDriver.api._

import scala.concurrent.Future

/**
* Created by robert on 03.02.16.
*/
@Singleton
class UserService @Inject() (val dbConfigProvider: DatabaseConfigProvider)
	extends HasDatabaseConfigProvider[JdbcProfile]{

	def countByName(name: String): Future[Int] = {
		db.run(
			users.filter(_.userName === name).countDistinct.result
		)
	}
}
