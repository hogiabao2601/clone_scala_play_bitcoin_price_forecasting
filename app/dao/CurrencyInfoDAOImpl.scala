package dao

import bao.ho.connection.DbProvider
import bao.ho.models.CurrencyInfo
import bao.ho.tables.CurrencyInfos
import javax.inject.{Inject, Singleton}

import scala.concurrent.Future

@Singleton
class CurrencyInfoDAOImpl @Inject()(protected val provider: DbProvider, table: CurrencyInfos) extends CurrencyInfoDAO {
  val driver = provider.getDriver()
  val db = provider.getDb()

  import driver.api._

  private val currencyInfos = table.getCurrencyInfos

  override def all(): Future[Seq[CurrencyInfo]] = db.run(currencyInfos.result)

  override def getPrice(currencyId: Int, from: Long, to: Long): Future[Seq[CurrencyInfo]] =
    db.run((for (currInfo <- currencyInfos
                 if currInfo.currencyId === currencyId &&
                   currInfo.timestamp.between(from, to)) yield currInfo).result)

}

