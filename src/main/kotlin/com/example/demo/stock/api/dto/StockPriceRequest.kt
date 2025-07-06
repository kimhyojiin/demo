package com.example.demo.stock.api.dto

import com.example.demo.stock.domain.StockPriceMeasurement
import java.time.Instant

data class StockPriceRequest(
    val symbol: String,
    val exchange: String,
    val currency: String,
    val open: Double,
    val high: Double,
    val low: Double,
    val close: Double,
    val volume: Long
) {
    fun toMeasurement(time: Instant): StockPriceMeasurement =
        StockPriceMeasurement(
            symbol = this.symbol,
            exchange = this.exchange,
            currency = this.currency,
            open = this.open,
            high = this.high,
            low = this.low,
            close = this.close,
            volume = this.volume,
            time = time
        )
}
