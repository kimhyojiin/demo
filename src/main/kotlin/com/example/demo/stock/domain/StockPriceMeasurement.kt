package com.example.demo.stock.domain

import com.influxdb.annotations.Column
import com.influxdb.annotations.Measurement
import java.time.Instant

@Measurement(name = "stock_price")
data class StockPriceMeasurement(
    @Column(tag = true) val symbol: String,
    @Column(tag = true) val exchange: String,
    @Column(tag = true) val currency: String,

    @Column val open: Double,
    @Column val high: Double,
    @Column val low: Double,
    @Column val close: Double,
    @Column val volume: Long,

    @Column(timestamp = true) val time: Instant
)