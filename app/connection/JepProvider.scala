package connection

import javax.inject.{Inject, Singleton}
import jep.Jep
import play.api.inject.ApplicationLifecycle
import play.api.Configuration
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class JepProvider @Inject()(
                             config: Configuration,
                             lifecycle: ApplicationLifecycle
                           )(implicit executionContext: ExecutionContext) {

  val jep = new Jep()

  jep.runScript(config
    .getOptional[String]("python")
    .getOrElse("./python/currency_price_prediction.py")
  )

  lifecycle.addStopHook { () =>
    Future.successful(jep.close())
  }
}
