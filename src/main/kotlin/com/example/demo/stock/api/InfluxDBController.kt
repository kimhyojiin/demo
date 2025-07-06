package com.example.demo.stock.api

import com.example.demo.stock.domain.SampleMeasurement
import com.example.demo.stock.service.InfluxDBService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/influx")
class InfluxDBController(
    private val influxDBService: InfluxDBService
) {
    @PostMapping("/measurement")
    suspend fun writeMeasurement(@RequestBody measurement: SampleMeasurement) {
        influxDBService.writeMeasurement(measurement)
    }

    @PostMapping("/point")
    suspend fun writePoint(
        @RequestParam(value = "host") host: String,
        @RequestParam(value = "used_percent") usedPercent: Double,
    ) {
        influxDBService.writePoint(host, usedPercent)
    }

    @GetMapping("/recent")
    suspend fun getRecentData() {
        return influxDBService.query()
    }
}
