import bao.ho.connection.DbProvider
import com.google.inject.AbstractModule
import connection.{JepProvider, PostgresDBProvider}
import dao.{CurrencyDAO, CurrencyDAOImpl, CurrencyInfoDAO, CurrencyInfoDAOImpl}
import jep.Jep
import services.{CurrencyInfoService, CurrencyInfoServiceImpl, PredictionService, PredictionServiceImpl}

/**
  * This class is a Guice module that tells Guice how to bind several
  * different types. This Guice module is created when the Play
  * application starts.
  *
  * Play will automatically use any class called `Module` that is in
  * the root package. You can create modules in other locations by
  * adding `play.modules.enabled` settings to the `application.conf`
  * configuration file.
  */
class Module extends AbstractModule {

  override def configure() = {
    bind(classOf[PredictionService]).to(classOf[PredictionServiceImpl])
//    bind(classOf[JepProvider]).asEagerSingleton()
    bind(classOf[DbProvider]).to(classOf[PostgresDBProvider])
    bind(classOf[CurrencyDAO]).to(classOf[CurrencyDAOImpl])
    bind(classOf[CurrencyInfoDAO]).to(classOf[CurrencyInfoDAOImpl])
    bind(classOf[CurrencyInfoService]).to(classOf[CurrencyInfoServiceImpl])
  }

}
