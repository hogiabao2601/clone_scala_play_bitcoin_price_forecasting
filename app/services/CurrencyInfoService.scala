package services

import bao.ho.models.CurrencyInfo
import dao.CurrencyInfoDAO

import scala.concurrent.Future

abstract class CurrencyInfoService(currencyInfoDAO: CurrencyInfoDAO) {
  def getPrice(currency: String, from: Long, to: Long): Future[Seq[CurrencyInfo]]
}
