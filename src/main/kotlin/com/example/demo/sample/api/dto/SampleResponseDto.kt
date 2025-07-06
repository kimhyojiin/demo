package com.example.demo.sample.api.dto

import java.time.LocalDate
import java.time.LocalDateTime

data class SampleResponseDto(
    val id: Long,
    val content: String,
    val name: String,
    val age: Int,
    val birthdate: LocalDate,
    val status: String? = null,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)
