package com.example.demo.sample.repository

import com.example.demo.sample.domain.Sample
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SampleRepository : JpaRepository<Sample, Long> {
}