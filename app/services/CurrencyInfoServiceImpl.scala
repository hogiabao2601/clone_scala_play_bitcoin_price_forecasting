package services

import bao.ho.models.CurrencyInfo
import dao.{CurrencyDAO, CurrencyInfoDAO}
import javax.inject.{Inject, Singleton}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class CurrencyInfoServiceImpl @Inject()(currencyDao: CurrencyDAO,
                                        currencyInfoDAO: CurrencyInfoDAO)
                                       (implicit executionContext: ExecutionContext) extends CurrencyInfoService(currencyInfoDAO) {

  override def getPrice(currency: String, from: Long, to: Long): Future[Seq[CurrencyInfo]] = {
    currencyDao.findByCurrency(currency) flatMap {
      case Some(curr) => currencyInfoDAO.getPrice(curr.id, from, to)
      case None => Future {
        Seq[CurrencyInfo]()
      }
    }
  }
}
