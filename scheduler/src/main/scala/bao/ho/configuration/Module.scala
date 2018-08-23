package bao.ho.configuration

import bao.ho.connection.{DbProvider, PostgresDBProvider}
import bao.ho.dao.{CurrencyDAO, CurrencyDAOImpl, CurrencyInfoDAO, CurrencyInfoDAOImpl}
import bao.ho.service.crawler.{CoinMarketCapCrawlerImpl, CurrencyCrawler}
import bao.ho.service.woker.{Worker, WorkerImpl}
import com.google.inject.AbstractModule
import com.typesafe.config.ConfigFactory

import play.api.Configuration
import play.api.libs.ws.WSClient
import play.api.libs.ws.ahc.AhcWSClient

import scala.concurrent.ExecutionContext


class Module extends AbstractModule with Global {


  override def configure() = {
    bind(classOf[CurrencyCrawler]).to(classOf[CoinMarketCapCrawlerImpl])
    bind(classOf[WSClient]).toInstance(AhcWSClient())
    bind(classOf[Worker]).to(classOf[WorkerImpl])
    bind(classOf[ExecutionContext])
      .toInstance(scala.concurrent.ExecutionContext.Implicits.global)
    bind(classOf[DbProvider]).to(classOf[PostgresDBProvider])
    bind(classOf[CurrencyDAO]).to(classOf[CurrencyDAOImpl])
    bind(classOf[CurrencyInfoDAO]).to(classOf[CurrencyInfoDAOImpl])
  }

}
