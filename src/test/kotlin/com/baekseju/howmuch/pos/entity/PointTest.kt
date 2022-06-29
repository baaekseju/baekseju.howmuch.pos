package com.baekseju.howmuch.pos.entity

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.Instant
import javax.validation.Validation
import javax.validation.Validator
import javax.validation.ValidatorFactory
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive

internal class PointTest {
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
    fun point() {
        val point = Point(
            point = 1000,
            user = User(id=1, phoneNumber = "010-4631-1684", createdAt = Instant.now(), updatedAt = Instant.now())
        )

        val violations = validator.validate(point)

        assertThat(violations).isEmpty()
    }

    @Test
    fun pointCheckNull() {
        val point = Point(
            point = null,
            user = null
        )

        val violations = validator.validate(point)

        assertThat(violations).isNotEmpty
        assertThat(violations.filter { it.constraintDescriptor.annotation is NotNull }.size).isEqualTo(2)
    }

    @Test
    fun pointCheckPositive() {
        val point = Point(
            point = 0,
            user = User(id=1, phoneNumber = "010-4631-1684", createdAt = Instant.now(), updatedAt = Instant.now())
        )

        val violations = validator.validate(point)

        assertThat(violations).isNotEmpty
        assertThat(violations.filter { it.constraintDescriptor.annotation is Positive }.size).isEqualTo(1)
    }
}
