package com.example.demo.sample.api.dto

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Size
import java.time.LocalDate

data class SampleUpdateRequestDto(
    @field:Size(max = 100)
    val content: String?,

    @field:Size(max = 10)
    val name: String?,

    @field:Min(0)
    @field:Max(150)
    val age: Int?,

    val birthdate: LocalDate?,

    @field:Size(max = 20)
    val status: String?
)
