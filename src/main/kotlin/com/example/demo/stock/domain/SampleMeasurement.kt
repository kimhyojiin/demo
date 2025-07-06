package com.example.demo.stock.domain

import com.influxdb.annotations.Column
import com.influxdb.annotations.Measurement
import java.time.Instant

@Measurement(name = "sample_measurement")
data class SampleMeasurement(
    @Column(tag = true)
    val host: String,

    @Column(name= "used_percent")
    val usedPercent: Double,

    @Column(timestamp = true)
    val time: Instant = Instant.now()
)