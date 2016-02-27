package models.users.daos

import com.google.inject.{Inject, Singleton}
import models.users.daos.UsersDefinitions._
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.driver.JdbcProfile
import slick.driver.PostgresDriver.api._

import scala.concurrent.Future

@Singleton
class UserDao @Inject() (val dbConfigProvider: DatabaseConfigProvider)
	extends HasDatabaseConfigProvider[JdbcProfile] {

	def countUsers = {
		db.run(users.countDistinct.result)
	}

	def find(id: Long): Future[Option[User]] = {
		db.run(
			users.filter(_.id === id).result.headOption
		)
	}

	def findByName(name: String): Future[Option[User]] = {
		db.run (
			users.filter( _.userName === name ).result.headOption
		)
	}
}
