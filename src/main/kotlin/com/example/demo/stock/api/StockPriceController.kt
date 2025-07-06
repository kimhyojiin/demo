package com.example.demo.stock.api

import com.example.demo.stock.api.dto.CloseStockPriceResponse
import com.example.demo.stock.api.dto.StockPriceRequest
import com.example.demo.stock.service.StockPriceService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/stock")
class StockPriceController(
    private val stockService: StockPriceService
) {
    @PostMapping
    suspend fun addStockPrice(@RequestBody request: StockPriceRequest): ResponseEntity<String> {
        stockService.writeStockPrice(request)
        return ResponseEntity.ok("Stock data written to InfluxDB")
    }

    @PostMapping("/{symbol}")
    suspend fun addStockPrices(@PathVariable symbol: String): ResponseEntity<String> {
        stockService.writeStockPrices(symbol)
        return ResponseEntity.ok("Stock generated bulk data written to InfluxDB")
    }


    @GetMapping("/{symbol}/close")
    suspend fun getClosePrices(@PathVariable symbol: String): ResponseEntity<List<CloseStockPriceResponse>> {
        return ResponseEntity.ok(stockService.readClosePrices(symbol))
    }
}