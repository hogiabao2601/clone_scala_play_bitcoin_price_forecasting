package bao.ho.dao

import bao.ho.connection.DbProvider
import bao.ho.models.Currency
import bao.ho.tables.Currencies
import javax.inject.Inject

import scala.concurrent.Future

class CurrencyDAOImpl @Inject()(protected val provider: DbProvider, table: Currencies) extends CurrencyDAO {

  val driver = provider.getDriver()
  val db = provider.getDb()

  import driver.api._

  private val currencies = table.getCurrencies

  override def find(id: Int): Future[Option[Currency]] = db.run((for (currency <- currencies if currency.id === id) yield currency).result.headOption)

  override def findByCurrency(currency: String): Future[Option[Currency]] =
    db.run((for (cur <- currencies if cur.currency === currency) yield cur).result.headOption)
}
