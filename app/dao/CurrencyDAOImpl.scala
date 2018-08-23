package dao

import bao.ho.connection.DbProvider
import bao.ho.tables.Currencies
import javax.inject.{Inject, Singleton}


@Singleton
class CurrencyDAOImpl @Inject()(protected val provider: DbProvider, table: Currencies) extends CurrencyDAO {

  val driver = provider.getDriver()
  val db = provider.getDb()

  import driver.api._

  private val currencies = table.getCurrencies

  override def findByCurrency(currency: String) =
    db.run((for (cur <- currencies if cur.currency === currency) yield cur).result.headOption)
}
