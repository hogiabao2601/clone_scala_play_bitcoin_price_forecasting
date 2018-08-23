package dao

import bao.ho.models.Currency

import scala.concurrent.Future

trait CurrencyDAO {
  def findByCurrency(currency: String): Future[Option[Currency]]
}

