package bao.ho.service.woker

import java.util.Date

import org.slf4j.LoggerFactory

import scala.concurrent.Future
import scala.util.{Failure, Success}
import scala.concurrent.ExecutionContext.Implicits.global

trait Worker {
  def importHistoricData(currency: String, from: Date, to: Date): Future[Seq[Future[Long]]]
}

object Worker{

  val logger = LoggerFactory.getLogger(this.getClass)

  def stop[T](f: Future[T]): Unit = {
    f.onComplete({
      case Success(_) =>
        System.exit(1)
      case Failure(f) =>
        logger.error(s"Failure :: $f")
        System.exit(1)
    })
  }
}