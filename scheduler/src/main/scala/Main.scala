import bao.ho.configuration.Injector
import bao.ho.service.woker.Worker
import bao.ho.converting.DateUtils
import org.slf4j.LoggerFactory


object Main extends Injector {

  def main(args: Array[String]): Unit = {
    val worker = injector.getInstance(classOf[Worker])
    val from = DateUtils.convertStringToDate("2015-01-01 00:00:00+0000")
    val to = DateUtils.convertStringToDate("2018-08-17 00:30:00+0000")
    val f = worker.importHistoricData("bitcoin", from, to)
    Worker.stop(f)
  }
}

