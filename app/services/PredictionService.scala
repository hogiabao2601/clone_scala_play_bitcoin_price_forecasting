package services


trait PredictionService {
  def predictPrice(currency: String): String
}
