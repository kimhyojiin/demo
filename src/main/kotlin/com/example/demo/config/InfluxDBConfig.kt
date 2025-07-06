package com.example.demo.config

import com.influxdb.client.kotlin.InfluxDBClientKotlin
import com.influxdb.client.kotlin.InfluxDBClientKotlinFactory
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "influxdb")
class InfluxDBConfig {
    lateinit var url: String
    lateinit var token: String
    lateinit var org: String
    lateinit var bucket: String
    lateinit var retentionPolicy: String

    @Bean
    fun influxDBClientKotlin(): InfluxDBClientKotlin {
        return InfluxDBClientKotlinFactory.create(url, token.toCharArray(), org, bucket)
    }
}
