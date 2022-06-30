package com.baekseju.howmuch.pos.entity

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import javax.validation.Validation
import javax.validation.Validator
import javax.validation.ValidatorFactory
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

internal class OrderTest {
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
    fun order() {
        val order = Order(
            totalPrice = 10000,
            payWith = "credit-card"
        )

        val violations = validator.validate(order)

        assertThat(violations).isEmpty()
    }

    @Test
    fun orderCheckNull() {
        val order = Order(
            totalPrice = null,
            payWith = null
        )

        val violations = validator.validate(order)


        assertThat(violations).isNotEmpty
        assertThat(violations.filter { it.constraintDescriptor.annotation is NotNull }.size).isEqualTo(1)
        assertThat(violations.filter { it.constraintDescriptor.annotation is NotBlank }.size).isEqualTo(1)
    }

    @Test
    fun orderCheckEmpty() {
        val order = Order(
            totalPrice = 5900,
            payWith = ""
        )

        val violations = validator.validate(order)


        assertThat(violations).isNotEmpty
        assertThat(violations.filter { it.constraintDescriptor.annotation is NotBlank }.size).isEqualTo(1)
    }

    @Test
    fun orderCheckBlank() {
        val order = Order(
            totalPrice = 5900,
            payWith = " "
        )

        val violations = validator.validate(order)


        assertThat(violations).isNotEmpty
        assertThat(violations.filter { it.constraintDescriptor.annotation is NotBlank }.size).isEqualTo(1)
    }

    @Test
    fun orderDtoCheckMin() {
        val order = Order(
            totalPrice = -1,
            payWith = "credit-card"
        )

        val violations = validator.validate(order)


        assertThat(violations).isNotEmpty
        assertThat(violations.filter { it.constraintDescriptor.annotation is Min }.size).isEqualTo(1)
    }
}
