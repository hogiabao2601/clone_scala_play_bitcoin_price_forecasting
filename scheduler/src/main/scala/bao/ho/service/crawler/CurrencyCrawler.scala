package bao.ho.service.crawler

import java.util.Date

import bao.ho.models.CurrencyInfo

import scala.concurrent.Future

trait CurrencyCrawler {
  def crawlCurrencyData(currency: String, from: Date, to: Date): Future[Seq[CurrencyInfo]]
}
