package bao.ho.models

import com.fasterxml.jackson.annotation.{JsonCreator, JsonProperty}
import play.api.libs.json.{Json, JsonConfiguration}
import play.api.libs.json.JsonNaming.SnakeCase

case class CurrencyInfo @JsonCreator()(@JsonProperty("timestamp") timestamp: Long,
                                       @JsonProperty("currency_id") currencyId: Int,
                                       @JsonProperty("market_cap_by_available_supply") marketCapByAvailableSupply: Long,
                                       @JsonProperty("price_usd") priceUsd: Double,
                                       @JsonProperty("volume") volume: Long)

object CurrencyInfo {
  implicit val config = JsonConfiguration(SnakeCase)
  implicit val currencyInfoJsonFormat = Json.format[CurrencyInfo]
  implicit val currencyInfoJsonWrite = Json.writes[CurrencyInfo]

}