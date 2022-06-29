package com.baekseju.howmuch.pos.dto

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import javax.validation.Validation
import javax.validation.Validator
import javax.validation.ValidatorFactory
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive

internal class PointDtoTest {
    private lateinit var validatorFactory: ValidatorFactory
    private lateinit var validator: Validator

    @BeforeEach
    fun setup() {
        validatorFactory = Validation.buildDefaultValidatorFactory()
        validator = validatorFactory.validator
    }

    @AfterEach
    fun close() {
        validatorFactory.close()
    }

    @Test
    fun pointDto() {
        val pointDto = PointDto(
            point = 1000
        )

        val violations = validator.validate(pointDto)

        assertThat(violations).isEmpty()
    }

    @Test
    fun pointDtoCheckNull() {
        val pointDto = PointDto(
            point = null
        )

        val violations = validator.validate(pointDto)

        assertThat(violations).isNotEmpty
        assertThat(violations.filter { it.constraintDescriptor.annotation is NotNull }.size).isEqualTo(1)
    }

    @Test
    fun pointDtoCheckPositive() {
        val pointDto = PointDto(
            point = 0
        )

        val violations = validator.validate(pointDto)

        assertThat(violations).isNotEmpty
        assertThat(violations.filter { it.constraintDescriptor.annotation is Positive }.size).isEqualTo(1)
    }
}
