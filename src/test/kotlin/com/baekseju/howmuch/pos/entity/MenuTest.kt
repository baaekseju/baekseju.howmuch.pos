package com.baekseju.howmuch.pos.entity

import org.assertj.core.api.Assertions.assertThat
import org.hibernate.validator.constraints.URL
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.Instant
import javax.validation.Validation
import javax.validation.Validator
import javax.validation.ValidatorFactory
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

internal class MenuTest {
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
    fun menu() {
        val menu = Menu(
            name = "싸이버거",
            imageUrl = "https://www.test.com",
            price = 5900,
            additionalPrice = 500,
            category = Category(1, "햄버거", Instant.now(), Instant.now()),
            stock = 999,
            hidden = false
        )

        val violation = validator.validate(menu)

        assertThat(violation).isEmpty()
    }

    @Test
    fun menuCheckNull() {
        val menu = Menu(
            name = null,
            imageUrl = null,
            price = null,
            additionalPrice = null,
            category = null,
            stock = null,
            hidden = null
        )

        val violations = validator.validate(menu)

        assertThat(violations).isNotEmpty
        assertThat(violations.filter { it.constraintDescriptor.annotation is NotEmpty }.size).isEqualTo(1)
        assertThat(violations.filter { it.constraintDescriptor.annotation is NotNull }.size).isEqualTo(5)
        assertThat(violations.filter { it.constraintDescriptor.annotation is NotBlank }.size).isEqualTo(1)
    }

    @Test
    fun menuCheckEmpty() {
        val menu = Menu(
            name = "",
            imageUrl = "",
            price = 5900,
            additionalPrice = 500,
            category = Category(1, "햄버거", Instant.now(), Instant.now()),
            stock = 999,
            hidden = false
        )

        val violations = validator.validate(menu)

        assertThat(violations).isNotEmpty
        assertThat(violations.filter { it.constraintDescriptor.annotation is NotEmpty }.size).isEqualTo(1)
        assertThat(violations.filter { it.constraintDescriptor.annotation is NotBlank }.size).isEqualTo(1)
    }

    @Test
    fun menuCheckBlank() {
        val menu = Menu(
            name = " ",
            imageUrl = " ",
            price = 5900,
            additionalPrice = 500,
            category = Category(1, "햄버거", Instant.now(), Instant.now()),
            stock = 999,
            hidden = false
        )

        val violations = validator.validate(menu)

        assertThat(violations).isNotEmpty
        assertThat(violations.filter { it.constraintDescriptor.annotation is URL }.size).isEqualTo(1)
        assertThat(violations.filter { it.constraintDescriptor.annotation is NotBlank }.size).isEqualTo(1)
    }

    @Test
    fun menuCheckURL() {
        val menu = Menu(
            name = "싸이버거",
            imageUrl = "image",
            price = 5900,
            additionalPrice = 500,
            category = Category(1, "햄버거", Instant.now(), Instant.now()),
            stock = 999,
            hidden = false
        )

        val violations = validator.validate(menu)

        assertThat(violations).isNotEmpty
        assertThat(violations.filter { it.constraintDescriptor.annotation is URL }.size).isEqualTo(1)
    }

    @Test
    fun menuCheckMin() {
        val menu = Menu(
            name = "싸이버거",
            imageUrl = "https://test.com",
            price = -1,
            additionalPrice = -1,
            category = Category(1, "햄버거", Instant.now(), Instant.now()),
            stock = -1,
            hidden = false
        )

        val violations = validator.validate(menu)

        assertThat(violations).isNotEmpty
        assertThat(violations.filter {it.constraintDescriptor.annotation is Min }.size).isEqualTo(3)
    }
}
