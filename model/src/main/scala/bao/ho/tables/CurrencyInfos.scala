package bao.ho.tables

import bao.ho.connection.DbProvider
import bao.ho.models.CurrencyInfo
import javax.inject.{Inject, Singleton}

@Singleton
class CurrencyInfos @Inject()(provider: DbProvider) {

  val driver = provider.getDriver()

  import driver.api._

  private val currencyInfos = TableQuery[CurrencyInfosTable]

  class CurrencyInfosTable(tag: Tag) extends Table[CurrencyInfo](tag, "currency_infos") {

    def timestamp = column[Long]("timestamp", O.PrimaryKey)

    def currencyId = column[Int]("currency_id")

    def marketCapByAvailableSupply = column[Long]("market_cap_by_available_supply")

    def priceUsd = column[Double]("price_usd")

    def volume = column[Long]("volume")

    def * = (timestamp, currencyId, marketCapByAvailableSupply, priceUsd, volume) <> ((CurrencyInfo.apply _).tupled, CurrencyInfo.unapply)
  }

  def getCurrencyInfos = currencyInfos

}

