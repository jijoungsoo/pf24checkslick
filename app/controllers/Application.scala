package controllers

import play.api._
import play.api.mvc._
import play.api.db._
import play.api.Play.current
import javax.inject.Inject
import play.api.db.slick._
import scala.slick.driver.JdbcProfile
import scala.concurrent.Await
import scala.concurrent.duration._

import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider




class Application  @Inject()(dbConfigProvider: DatabaseConfigProvider) extends Controller {
    def slick = Action {
     var outString = "Number is "
    val test :TestSlick = new TestSlick(dbConfigProvider);
     
     Ok("outString"+test.slick())
  }
}

class TestSlick @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile] {
  import driver.api._
  def slick():Int = {
     val dbConfig = DatabaseConfigProvider.get[JdbcProfile]("default")(Play.current)

     val action = sql"SELECT COUNT(*) AS CNT FROM TB_USER".as[(Int)]
     val db=dbConfig.db
     val future = db.run(action)
     val tmp = Await.result(future, Duration.Inf)
     return tmp.apply(0);
    }

}
