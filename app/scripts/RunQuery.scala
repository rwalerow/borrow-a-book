package scripts

/**
	* Created by robert on 25.01.16.
	*/
object RunQuery {

	import play.api._

	val env = Environment( new java.io.File( "." ), this.getClass.getClassLoader, Mode.Dev )
	val context = ApplicationLoader.createContext( env )
	val loader = ApplicationLoader( context )
	val app = loader.load( context )
	Play.start( app )

	import Play.current

	import slick.driver.PostgresDriver.api._
	import scala.concurrent._
	import scala.concurrent.duration._

	val db = Database.forConfig( "consoleconnection" )

	def run[ T ](action: DBIO[ T ]) = {

		val result = Await.result( db.run( action ), 3 seconds )
		println(result)
	}
}
