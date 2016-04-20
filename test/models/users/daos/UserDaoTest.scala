package models.users.daos

import org.scalatest.OptionValues
import utils.PlayDbSpec

import scala.concurrent.Await
import scala.concurrent.duration._

class UserDaoTest extends PlayDbSpec with OptionValues {

	val userDao: UserDao = app.injector.instanceOf(classOf[UserDao])

	"UserDao" should {
		val result = Await.result(userDao.countUsers, 1 second)
		"fresh db count 1" in {
			result mustEqual 1
		}

		"fresh db should not by 0" in {
			result must not equal 0
		}

		"find admin by name" in {
			val admin = Await.result(userDao.findByName("admin"), 1 second)

			admin mustBe defined

			admin.map(_.userName) mustEqual Some("admin")
			admin.map(_.email) mustEqual Some("admin@home.pl")
		}
	}
}
