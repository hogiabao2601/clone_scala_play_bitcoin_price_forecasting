package bao.ho.service.woker

import java.util.Date

import bao.ho.dao.{CurrencyDAO, CurrencyInfoDAO}
import bao.ho.models.{Currency, CurrencyInfo}
import bao.ho.service.crawler.CurrencyCrawler
import javax.inject.Inject
import org.slf4j.LoggerFactory

import scala.concurrent.{ExecutionContext, Future}


class WorkerImpl @Inject()(crawler: CurrencyCrawler, currencyDao: CurrencyDAO, currencyInfoDAO: CurrencyInfoDAO)
                          (implicit executionContext: ExecutionContext)
  extends Worker {
//  val logger = LoggerFactory.getLogger(this.getClass)

  def importHistoricData(currency: String, from: Date, to: Date): Future[Seq[Future[Long]]] = {
    val currencyF: Future[Option[Currency]] = currencyDao.findByCurrency(currency)
    val currencyInfosF: Future[Seq[CurrencyInfo]] = crawler.crawlCurrencyData(currency, from, to)

    for {
      currOpt: Option[Currency] <- currencyF
      currInfos: Seq[CurrencyInfo] <- currencyInfosF
    } yield for {
      currInfo <- currInfos
    } yield currencyInfoDAO.insert(currInfo.copy(currencyId = currOpt.get.id))
  }
}
