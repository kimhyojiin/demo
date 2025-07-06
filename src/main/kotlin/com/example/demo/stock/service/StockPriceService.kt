package com.example.demo.stock.service

import com.example.demo.stock.api.dto.StockPriceRequest
import com.example.demo.stock.api.dto.CloseStockPriceResponse
import com.influxdb.client.domain.WritePrecision
import com.influxdb.client.kotlin.InfluxDBClientKotlin
import com.influxdb.client.write.Point
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Service
import java.time.Instant
import kotlin.random.Random

@Service
class StockPriceService(
    private val influxDBClientKotlin: InfluxDBClientKotlin,
) {
    suspend fun writeStockPrice(request: StockPriceRequest) {
        val point = Point("stock_price")
            .addTag("symbol", request.symbol)
            .addTag("exchange", request.exchange)
            .addTag("currency", request.currency)
            .addField("open", request.open)
            .addField("high", request.high)
            .addField("low", request.low)
            .addField("close", request.close)
            .addField("volume", request.volume)
            .time(Instant.now(), WritePrecision.NS)

        influxDBClientKotlin.getWriteKotlinApi().writePoint(point)
    }

    suspend fun writeStockPrices(symbol: String) {
        val now = Instant.now()
        val stockPriceRequests = generateStockPrices(symbol, 1000)

        influxDBClientKotlin.getWriteKotlinApi().writeMeasurements(
            stockPriceRequests.mapIndexed { index, request ->
                request.toMeasurement(now.minusSeconds((stockPriceRequests.size - index) * 60L))
            },
            WritePrecision.NS
        )
    }

    private fun generateStockPrices(symbol: String, count: Int): List<StockPriceRequest> {
        val basePrice = 100.0
        val random = Random.Default

        return List(count) { i ->
            val open = basePrice + random.nextDouble(-2.0, 2.0)
            val high = open + random.nextDouble(0.5, 3.0)
            val low = open - random.nextDouble(0.5, 3.0)
            val close = low + random.nextDouble(0.0, (high - low))
            val volume = random.nextLong(1_000_000, 5_000_000)

            StockPriceRequest(
                symbol = symbol,
                exchange = "NASDAQ",
                currency = "USD",
                open = open,
                high = high,
                low = low,
                close = close,
                volume = volume
            )
        }
    }fun findRecentCandlesticks(symbol: String, limit: Int): Flow<CandleStickResponseDto> {
        val fluxQuery = """
            from(bucket: "${influxDBProperties.bucket}")")
              |> range(start: -1d)
              |> filter(fn: (r) => r._measurement == "candlestick")
              |> filter(fn: (r) => r.symbol == "$symbol")
              |> aggregateWindow(every: 1m, fn: last)
              |> sort(columns: .getValueByKey("_time"], desc: false)
              |> limit(n: ${limit})
        """.trimIndent()

        return influxDBClient.getQueryKotlinApi()
            .query(fluxQuery)
            .consumeAsFlow()
            .map { record ->
                CandleStickResponseDto(
                    symbol = record.getValueByKey("symbol") as String,
                    open = (record.getValueByKey("open") as Number).toDouble(),
                    close = (record.getValueByKey("close") as Number).toDouble(),
                    high = (record.getValueByKey("high") as Number).toDouble(),
                    low = (record.getValueByKey("low") as Number).toDouble(),
                    volume = (record.getValueByKey("volume") as Number).toLong(),
                    startTime = record.time
                )
            }
    }

    suspend fun readClosePrices(symbol: String): List<CloseStockPriceResponse> {
        val query = """
            from(bucket: "bucket1")
            |> range(start: -7d)
            |> filter(fn: (r) => r._measurement == "stock_price" and r.symbol == "$symbol" and r._field == "close")
          """.trimIndent()

        return influxDBClientKotlin.getQueryKotlinApi().query(query)
            .consumeAsFlow()
            .map { record ->
                CloseStockPriceResponse(
                    symbol = record.getValueByKey("symbol") as String,
                    close = (record.value as Number).toDouble(),
                )
            }.toList()
    }

}