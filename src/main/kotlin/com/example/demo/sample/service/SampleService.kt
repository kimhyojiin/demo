package com.example.demo.sample.service

import com.example.demo.sample.api.dto.SampleCreateRequestDto
import com.example.demo.sample.api.dto.SampleResponseDto
import com.example.demo.sample.api.dto.SampleUpdateRequestDto
import com.example.demo.sample.mapper.SampleMapper
import com.example.demo.sample.repository.SampleRepository
import org.springframework.stereotype.Service

@Service
class SampleService(
    private val sampleRepository: SampleRepository,
    private val sampleMapper: SampleMapper,
) {
    fun getSamples(): List<SampleResponseDto> {
        val samples = sampleRepository.findAll()
        return sampleMapper.toResponseList(samples)
    }

    fun getSample(id: Long): SampleResponseDto {
        val sample = sampleRepository.findById(id).orElseThrow()
        return sampleMapper.toResponse(sample)
    }

    fun create(request: SampleCreateRequestDto): SampleResponseDto {
        val entity = sampleMapper.toEntity(request)
        val saved = sampleRepository.save(entity)
        return sampleMapper.toResponse(saved)
    }

    fun update(id: Long, request: SampleUpdateRequestDto): SampleResponseDto {
        val entity = sampleRepository.findById(id).orElseThrow()
        sampleMapper.updateEntityFromDto(request, entity)
        return sampleMapper.toResponse(sampleRepository.save(entity))
    }
}