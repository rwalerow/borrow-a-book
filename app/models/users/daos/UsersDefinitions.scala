package models.users.daos

import java.sql.Timestamp

import slick.driver.PostgresDriver.api._

/**
  * Created by robert on 23.01.16.
  */
object UsersDefinitions {

	val dbSchema = Some("borrowabook")

	/**
		* Case classes definitions
		*/

	case class User (
		id: Long,
		userName: String,
		email: String,
		createdAt: Timestamp
	)

	case class LoginInfo (
		id: Long,
		providerID: String,
		providerKey: String
	)

	case class UserLoginInfo (
		id: Long,
		userId: Long,
		loginInfoId: Long
	)

	case class PasswordInfo (
		id: Long,
		hasher: String,
		password: String,
		salt: String,
		loginInfoId: Long,
		createdAt: Timestamp
	)

	/**
		* Table mappings
		*/

	class Users(tag: Tag) extends Table[User](tag, dbSchema, "users"){
		def id        = column[Long]("id", O.PrimaryKey, O.AutoInc)
		def userName  = column[String]("user_name")
		def email     = column[String]("email")
		def createdAt = column[Timestamp]("created_at")

		def * = (id, userName, email, createdAt) <> (User.tupled, User.unapply)
	}

	class LoginInfos(tag: Tag) extends Table[LoginInfo](tag, dbSchema, "login_infos"){
		def id          = column[Long]("id", O.PrimaryKey, O.AutoInc)
		def providerID  = column[String]("provider_id")
		def providerKey = column[String]("provider_key")

		def * = (id, providerID, providerKey) <> (LoginInfo.tupled, LoginInfo.unapply)
	}

	class UserLoginInfos(tag: Tag) extends Table[UserLoginInfo](tag, dbSchema, "user_login_infos"){
		def id      = column[Long]("id", O.PrimaryKey, O.AutoInc)
		def userId  = column[Long]("user_id")
		def loginInfoId = column[Long]("login_info_id")

		def * = (id, userId, loginInfoId) <> (UserLoginInfo.tupled, UserLoginInfo.unapply)
	}

	class PasswordInfos(tag: Tag) extends Table[PasswordInfo](tag, dbSchema, "password_infos"){
		def id            = column[Long]("id", O.PrimaryKey, O.AutoInc)
		def hasher        = column[String]("hasher")
		def password      = column[String]("password")
		def salt          = column[String]("salt")
		def loginInfoId   = column[Long]("login_info_id")
		def createdAt     = column[Timestamp]("created_at")

		def * = (id, hasher, password, salt, loginInfoId, createdAt) <> (PasswordInfo.tupled, PasswordInfo.unapply)
	}

	/**
		* Queries
		*/

	lazy val users = TableQuery[Users]
	lazy val loginInfos = TableQuery[LoginInfos]
	lazy val userLoginInfos = TableQuery[UserLoginInfos]
	lazy val passwordInfos = TableQuery[PasswordInfos]

}