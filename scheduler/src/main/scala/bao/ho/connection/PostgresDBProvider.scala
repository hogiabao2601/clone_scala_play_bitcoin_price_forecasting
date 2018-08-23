package bao.ho.connection

import org.slf4j.LoggerFactory
import slick.jdbc.{JdbcProfile, PostgresProfile}


class PostgresDBProvider() extends DbProvider {
//  val LOG = LoggerFactory.getLogger(this.getClass)

  val driver = PostgresProfile

  import driver.api._

  val db: Database = PostgresDB.connectionPool

  private[connection] object PostgresDB {

    import slick.jdbc.PostgresProfile.api._

    val connectionPool = Database.forConfig("postgres")
  }

  override def getDriver(): JdbcProfile = driver

  override def getDb() = db
}
