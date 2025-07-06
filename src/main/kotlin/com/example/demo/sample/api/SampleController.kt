package com.example.demo.sample.api

import com.example.demo.sample.api.dto.SampleCreateRequestDto
import com.example.demo.sample.api.dto.SampleResponseDto
import com.example.demo.sample.api.dto.SampleUpdateRequestDto
import com.example.demo.sample.service.SampleService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class SampleController(
    private val sampleService: SampleService,
) {
    @PostMapping("/sample")
    fun createSample(
        @Valid @RequestBody sampleCreateRequestDto: SampleCreateRequestDto,
    ): ResponseEntity<SampleResponseDto> {
        return ResponseEntity.ok(sampleService.create(sampleCreateRequestDto))
    }

    @PutMapping("/sample/{id}")
    fun updateSample(
        @PathVariable id: Long,
        @Valid @RequestBody sampleUpdateRequestDto: SampleUpdateRequestDto,
    ): ResponseEntity<SampleResponseDto> {
        return ResponseEntity.ok(sampleService.update(id, sampleUpdateRequestDto))
    }

    @GetMapping("/samples")
    fun samples(): ResponseEntity<List<SampleResponseDto>> {
        return ResponseEntity.ok(sampleService.getSamples())
    }

    @GetMapping("/sample/{id}")
    fun sample(
        @PathVariable id: Long
    ): ResponseEntity<SampleResponseDto> {
        return ResponseEntity.ok(sampleService.getSample(id))
    }
}