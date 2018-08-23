package bao.ho.dao

import bao.ho.connection.DbProvider
import bao.ho.models.CurrencyInfo
import bao.ho.tables.CurrencyInfos
import javax.inject.Inject

import scala.concurrent.Future

class CurrencyInfoDAOImpl @Inject()(protected val provider: DbProvider, table: CurrencyInfos) extends CurrencyInfoDAO {
  val driver = provider.getDriver()
  val db = provider.getDb()

  import driver.api._

  private val currencyInfos = table.getCurrencyInfos

  def insert(currencyInfo: CurrencyInfo): Future[Long] = db.run((currencyInfos returning currencyInfos.map(_.timestamp)) += currencyInfo)
}

