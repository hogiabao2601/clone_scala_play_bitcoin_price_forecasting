package services

import connection.JepProvider
import javax.inject.Inject


class PredictionServiceImpl @Inject()(jepProvider: JepProvider) extends PredictionService {
  override def predictPrice(currency: String): String = {
    val a = 2
    val b = 3
    // There are multiple ways to evaluate. Let us demonstrate them:
    println(jepProvider.jep)
    jepProvider.jep.eval(s"c = add($a, $b)")
    val ans1 = jepProvider.jep.getValue("c").asInstanceOf[Int]
    println(ans1)
    jepProvider.jep.eval(s"price = predictPrice($currency)")
    val ans = jepProvider.jep.getValue("price").asInstanceOf[String]
    ans
  }
}
