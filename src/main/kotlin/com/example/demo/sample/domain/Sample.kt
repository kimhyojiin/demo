package com.example.demo.sample.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "sample")
class Sample(
    @field:NotBlank
    @Column(length = 100, nullable = false)
    var content: String,

    @field:NotBlank
    @field:Size(max = 10)
    @Column(length = 10, nullable = false)
    var name: String,

    @field:Min(0)
    @field:Max(150)
    @Column(nullable = false)
    var age: Int,

    @field:NotNull
    @Column(nullable = false)
    var birthdate: LocalDate,

    @field:Size(max = 20)
    @Column(length = 20)
    var status: String? = null,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.now()

    @UpdateTimestamp
    @Column(nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now()

    // 기본 생성자
    protected constructor() : this(
        content = "",
        name = "",
        age = 0,
        birthdate = LocalDate.now()
    )
}