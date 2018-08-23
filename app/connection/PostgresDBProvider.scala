package connection

import bao.ho.connection.DbProvider
import javax.inject.{Inject, Singleton}
import org.slf4j.LoggerFactory
import play.api.inject.ApplicationLifecycle
import slick.jdbc.{JdbcProfile, PostgresProfile}

import scala.concurrent.{ExecutionContext, Future}


@Singleton
class PostgresDBProvider @Inject()(lifecycle: ApplicationLifecycle)(implicit executionContext: ExecutionContext) extends DbProvider {
  val LOG = LoggerFactory.getLogger(this.getClass)

  val driver = PostgresProfile

  import driver.api._

  val db: Database = PostgresDB.connectionPool

  private[connection] object PostgresDB {

    import slick.jdbc.PostgresProfile.api._

    val connectionPool = Database.forConfig("postgres")
  }

  override def getDriver(): JdbcProfile = driver

  override def getDb() = db

  lifecycle.addStopHook { () =>
    Future.successful(db.close())
  }
}
