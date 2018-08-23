package bao.ho.service.crawler

import java.util.Date

import bao.ho.models.CurrencyInfo
import bao.ho.converting.DateUtils
import javax.inject.Inject
import play.api.libs.json.{JsValue, Json}
import play.api.libs.ws.{WSClient, WSResponse}

import scala.concurrent.ExecutionContext.Implicits._
import scala.concurrent.Future

class CoinMarketCapCrawlerImpl @Inject()(wsClient: WSClient) extends CurrencyCrawler {
  override def crawlCurrencyData(currency: String, from: Date, to: Date): Future[Seq[CurrencyInfo]] = {

    /**
      * create Seq[CurrencyInfo] from response body of coin market cap
      *
      * @param wsResponse
      * @return
      */
    def parseData(wsResponse: WSResponse): Seq[CurrencyInfo] = {
      type MarketCapData = Seq[Seq[Double]]
      val MARKET_CAP_BY_AVAILABLE_SUPPLY = "market_cap_by_available_supply"
      val PRICE_USD = "price_usd"
      val VOLUME_USD = "volume_usd"
      val NA_ID = -1

      val json: JsValue = Json.parse(wsResponse.body)
      val data = (json(MARKET_CAP_BY_AVAILABLE_SUPPLY).as[MarketCapData]
        .toStream
        .map(row => row.head -> row.last) ++
        json(PRICE_USD).as[MarketCapData]
          .toStream
          .map(row => row.head -> row.last) ++
        json(VOLUME_USD).as[MarketCapData]
          .toStream
          .map(row => row.head -> row.last))
        .groupBy(_._1)
        .mapValues(_.map(_._2))
        .map {
          case (key, value) => CurrencyInfo(
            key.toLong,
            NA_ID,
            value.head.toLong,
            value(1),
            value(2).toLong)
        }.toSeq
      println(data.length)
      data
    }

    val url =
      s"""https://graphs2.coinmarketcap.com/currencies/
         |${currency.toLowerCase}/
         |${DateUtils.convertDateToEpoch(from)}/
         |${DateUtils.convertDateToEpoch(to)}/""".stripMargin.replaceAll("\n", "")

    wsClient.url(url).get().map(parseData)
      .andThen { case _ => wsClient.close() }
  }
}
