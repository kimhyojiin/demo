package com.example.demo.stock.service

import com.example.demo.stock.domain.SampleMeasurement
import com.influxdb.client.domain.WritePrecision
import com.influxdb.client.kotlin.InfluxDBClientKotlin
import com.influxdb.client.write.Point
import kotlinx.coroutines.flow.consumeAsFlow
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class InfluxDBService(
    private val influxDBClientKotlin: InfluxDBClientKotlin
) {
    // 데이터 쓰기
    suspend fun writeMeasurement(measurement: SampleMeasurement) {
        influxDBClientKotlin.getWriteKotlinApi().writeMeasurement(
            measurement,
            WritePrecision.NS
        )
    }

    // Point 객체를 사용한 데이터 쓰기
    suspend fun writePoint(host: String, usedPercent: Double) {
        val point = Point.measurement("sample_measurement")
            .addTag("host", host)
            .addField("used_percent", usedPercent)
            .time(Instant.now(), WritePrecision.NS)

        influxDBClientKotlin.getWriteKotlinApi().writePoint(point)
    }

    // 최근 데이터 조회
    suspend fun query() {
        val query = """from(bucket: "bucket1")
            |> range(start: -1d)
        """

        val results = influxDBClientKotlin.getQueryKotlinApi().query(query)

        return results.consumeAsFlow().collect { println("$it") }
    }
}