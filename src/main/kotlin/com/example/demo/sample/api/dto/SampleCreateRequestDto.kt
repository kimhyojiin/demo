package com.example.demo.sample.api.dto

import jakarta.validation.constraints.*
import java.time.LocalDate

data class SampleCreateRequestDto(
    @field:NotBlank
    @field:Size(max = 100)
    val content: String,

    @field:NotBlank
    @field:Size(max = 10)
    val name: String,

    @field:Min(0)
    @field:Max(150)
    val age: Int,

    @field:NotNull
    val birthdate: LocalDate,

    @field:Size(max = 20)
    val status: String? = null,
)
