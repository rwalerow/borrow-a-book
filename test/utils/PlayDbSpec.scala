package utils

import com.typesafe.config.ConfigFactory
import org.scalatest.ShouldMatchers
import org.scalatestplus.play.{OneAppPerSuite, PlaySpec}
import play.api.Play
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile

/**
	* Created by robert on 08.02.16.
	*/
trait PlayDbSpec extends PlaySpec with OneAppPerSuite {

	val conf = ConfigFactory.load
	lazy val dbConfig = DatabaseConfigProvider.get[ JdbcProfile ]( Play.current )
	lazy val db = dbConfig.db

}
