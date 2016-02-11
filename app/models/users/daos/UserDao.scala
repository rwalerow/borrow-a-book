package models.users.daos

import com.google.inject.{Inject, Singleton}
import models.users.daos.UsersDefinitions._
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.driver.JdbcProfile
import slick.driver.PostgresDriver.api._

@Singleton
class UserDao @Inject() (val dbConfigProvider: DatabaseConfigProvider)
	extends HasDatabaseConfigProvider[JdbcProfile] {

	def countUsers = {
		db.run(users.countDistinct.result)
	}
}
