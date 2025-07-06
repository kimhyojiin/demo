package com.example.demo.sample.mapper

import com.example.demo.sample.api.dto.SampleCreateRequestDto
import com.example.demo.sample.api.dto.SampleResponseDto
import com.example.demo.sample.api.dto.SampleUpdateRequestDto
import com.example.demo.sample.domain.Sample
import org.mapstruct.BeanMapping
import org.mapstruct.Mapper
import org.mapstruct.MappingTarget
import org.mapstruct.NullValuePropertyMappingStrategy

@Mapper(componentModel = "spring")
interface SampleMapper {
    fun toResponse(entity: Sample): SampleResponseDto

    fun toEntity(dto: SampleCreateRequestDto): Sample

    fun toResponseList(entities: List<Sample>): List<SampleResponseDto>

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    fun updateEntityFromDto(dto: SampleUpdateRequestDto, @MappingTarget entity: Sample)
}