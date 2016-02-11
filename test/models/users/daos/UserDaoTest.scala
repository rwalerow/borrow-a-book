package models.users.daos

import utils.PlayDbSpec

import scala.concurrent.Await
import scala.concurrent.duration._

class UserDaoTest extends PlayDbSpec {

	val userDao : UserDao = app.injector.instanceOf(classOf[UserDao])

	"UserDao" should {
		val result = Await.result(userDao.countUsers, 1 second)
		"Fresh db count 1" in {

			result mustEqual 1
		}

		"Fresh db should not by 0" in {

			result must not equal 0
		}
	}
}
