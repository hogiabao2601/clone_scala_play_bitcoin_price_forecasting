package controllers

import java.util.Calendar

import bao.ho.converting.DateUtils
import javax.inject._
import jep.Jep
import play.api.Configuration
import play.api.libs.json.Json
import play.api.mvc._
import services.CurrencyInfoService

import scala.concurrent.ExecutionContext

class PredictionController @Inject()(config: Configuration,
                                     currencyInfoService: CurrencyInfoService,
                                     cc: ControllerComponents
                                    )(implicit executionContext: ExecutionContext) extends AbstractController(cc) {
  /**
    * Use this endpoint to get the predict price of currency.
    *
    * @param currency
    * @return
    */
  def predictPrice(currency: String) = Action.async {
    val cal = Calendar.getInstance()
    val now = cal.getTime.getTime
    cal.add(Calendar.DATE, -15)
    val last15Days = cal.getTime.getTime

    val today = Calendar.getInstance()

    currencyInfoService
      .getPrice(currency, last15Days, now)
      .map(f = data => {
        val result = data
          .map(row => (DateUtils.getDateAsString(
            DateUtils.convertEpochToDate(row.timestamp)), row.priceUsd))
          .groupBy(_._1)
          .mapValues(_.map(_._2))
          .mapValues(row => row.sum / row.length)
          .toSeq
          .sortBy(_._1)
          .map(_._2)
        val predictData = JepProvider.predictData(currency, result)
        val predictList = predictData
          .replace("[", "")
          .replace("]", "")
          .split(" ")
          .filter(_ != "")
          .zipWithIndex
          .map { case (e, i) => {
            today.add(Calendar.DATE, 1)
            (DateUtils.getDateAsString(today.getTime), e)
          }
          }
        Ok(Json.toJson(predictList))
      })
  }

  object JepProvider {
    val jep = new Jep()

    jep.runScript(config
      .getOptional[String]("python")
      .getOrElse("./python/currency_price_prediction.py")
    )

    def predictData(currency: String, data: Seq[Double]): String = {
      val fn = "predict_price"
      val last15Days = data.mkString(" ")
      jep.invoke(fn, last15Days, currency).asInstanceOf[String]
    }
  }

}
