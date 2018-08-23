package controllers

import bao.ho.converting.DateUtils
import javax.inject._
import play.api.libs.json.Json
import play.api.mvc._
import services.CurrencyInfoService

import scala.concurrent.{ExecutionContext, Future}

class ApplicationController @Inject()(currencyInfoService: CurrencyInfoService,
                                      cc: ControllerComponents
                                     )(implicit executionContext: ExecutionContext) extends AbstractController(cc) {
  /**
    * Use to get price of the currency base timestamp
    *
    * @param currency
    * @param from
    * @param to
    * @return
    */
  def getPrice(currency: String,
               from: Long,
               to: Long): Action[AnyContent] = Action.async {

    currencyInfoService
      .getPrice(currency, from, to)
      .map(data => Ok(Json.toJson(data)))
  }

  /**
    * Use to get average price of the currency base timestamp
    *
    * @param currency
    * @param from
    * @param to
    * @return
    */
  def getAveragePrice(currency: String,
                      from: Long,
                      to: Long): Action[AnyContent] = Action.async {

    currencyInfoService
      .getPrice(currency, from, to)
      .map(data => {
        val result = data
          .map(row => (DateUtils.getDateAsString(
            DateUtils.convertEpochToDate(row.timestamp)), row.priceUsd))
          .groupBy(_._1)
          .mapValues(_.map(_._2))
          .mapValues(row => row.sum / row.length)
          .toSeq
          .sortBy(_._1)
        Ok(Json.toJson(result))
      })


  }

  def index() = Action.async {
    Future {
      Ok(s"Welcome to Crypto currency world ")
    }
  }
}
