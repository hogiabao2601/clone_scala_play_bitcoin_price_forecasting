package bao.ho.dao

import bao.ho.models.CurrencyInfo

import scala.concurrent.Future

trait CurrencyInfoDAO {
  def insert(currencyInfo: CurrencyInfo): Future[Long]
}