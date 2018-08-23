package dao

import bao.ho.models.CurrencyInfo

import scala.concurrent.Future


trait CurrencyInfoDAO {
  def all(): Future[Seq[CurrencyInfo]]

  def getPrice(currencyId: Int, from: Long, to: Long): Future[Seq[CurrencyInfo]]
}