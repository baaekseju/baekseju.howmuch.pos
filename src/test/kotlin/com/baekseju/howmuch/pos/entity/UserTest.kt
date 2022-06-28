package com.baekseju.howmuch.pos.entity

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import javax.validation.Validation
import javax.validation.Validator
import javax.validation.ValidatorFactory
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

internal class UserTest {
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
    fun user() {
        val user = User(
            phoneNumber = "010-4631-1684"
        )

        val violations = validator.validate(user)

        assertThat(violations).isEmpty()
    }

    @Test
    fun userCheckNull() {
        val user = User(
            phoneNumber = null
        )

        val violations = validator.validate(user)

        assertThat(violations).isNotEmpty
        assertThat(violations.filter { it.constraintDescriptor.annotation is NotNull }.size).isEqualTo(1)
    }

    @Test
    fun userCheckEmpty() {
        val user = User(
            phoneNumber = ""
        )

        val violations = validator.validate(user)

        assertThat(violations).isNotEmpty
        assertThat(violations.filter { it.constraintDescriptor.annotation is Pattern }.size).isEqualTo(1)
    }

    @Test
    fun userCheckPattern() {
        val user = User(
            phoneNumber = "010-12312-13123"
        )

        val violations = validator.validate(user)

        assertThat(violations).isNotEmpty
        assertThat(violations.filter { it.constraintDescriptor.annotation is Pattern }.size).isEqualTo(1)
    }
}
