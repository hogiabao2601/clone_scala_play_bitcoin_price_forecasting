package bao.ho.tables

import bao.ho.connection.DbProvider
import bao.ho.models.Currency
import javax.inject.{Inject, Singleton}

@Singleton
class Currencies @Inject()(provider: DbProvider) {

  val driver = provider.getDriver()

  import driver.api._

  private val currencies = TableQuery[CurrenciesTable]

  class CurrenciesTable(tag: Tag) extends Table[Currency](tag, "currencies") {

    def id = column[Int]("id", O.PrimaryKey)

    def currency = column[String]("currency")

    def abbreviation = column[String]("abbreviation")

    def currencyType = column[String]("currency_type")

    def * = (id, currency, abbreviation, currencyType) <> ((Currency.apply _).tupled, Currency.unapply)
  }

  def getCurrencies = currencies

}

