package com.baekseju.howmuch.pos.dto

import org.assertj.core.api.Assertions.assertThat
import org.hibernate.validator.constraints.URL
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import javax.validation.Validation
import javax.validation.Validator
import javax.validation.ValidatorFactory
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

internal class MenuDtoTest {
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
    fun menuDto() {
        val menuDto = MenuDto(
            name = "싸이버거",
            imageUrl = "https://www.test.com",
            price = 5900,
            additionalPrice = 500,
            categoryId = 1,
            stock = 999,
            hidden = false
        )

        val violations = validator.validate(menuDto)

        assertThat(violations).isEmpty()
    }

    @Test
    fun menuDtoCheckNull() {
        val menuDto = MenuDto(
            name = null,
            imageUrl = null,
            price = null,
            additionalPrice = null,
            categoryId = null,
            stock = null,
            hidden = null
        )

        val violations = validator.validate(menuDto)

        assertThat(violations).isNotEmpty
        assertThat(violations.filter { it.constraintDescriptor.annotation is NotEmpty }.size).isEqualTo(1)
        assertThat(violations.filter { it.constraintDescriptor.annotation is NotNull }.size).isEqualTo(5)
        assertThat(violations.filter { it.constraintDescriptor.annotation is NotBlank }.size).isEqualTo(1)
    }

    @Test
    fun menuDtoCheckEmpty() {
        val menuDto = MenuDto(
            name = "",
            imageUrl = "",
            price = 5900,
            additionalPrice = 500,
            categoryId = 1,
            stock = 999,
            hidden = false
        )

        val violations = validator.validate(menuDto)

        assertThat(violations).isNotEmpty
        assertThat(violations.filter { it.constraintDescriptor.annotation is NotEmpty }.size).isEqualTo(1)
        assertThat(violations.filter { it.constraintDescriptor.annotation is NotBlank }.size).isEqualTo(1)
    }

    @Test
    fun menuDtoCheckBlank() {
        val menuDto = MenuDto(
            name = " ",
            imageUrl = " ",
            price = 5900,
            additionalPrice = 500,
            categoryId = 1,
            stock = 999,
            hidden = false
        )

        val violations = validator.validate(menuDto)

        assertThat(violations).isNotEmpty
        assertThat(violations.filter { it.constraintDescriptor.annotation is URL }.size).isEqualTo(1)
        assertThat(violations.filter { it.constraintDescriptor.annotation is NotBlank }.size).isEqualTo(1)
    }

    @Test
    fun menuDtoCheckURL() {
        val menuDto = MenuDto(
            name = "싸이버거",
            imageUrl = "image",
            price = 5900,
            additionalPrice = 500,
            categoryId = 1,
            stock = 999,
            hidden = false
        )

        val violations = validator.validate(menuDto)

        assertThat(violations).isNotEmpty
        assertThat(violations.filter { it.constraintDescriptor.annotation is URL }.size).isEqualTo(1)
    }

    @Test
    fun menuDtoCheckMin() {
        val menuDto = MenuDto(
            name = "싸이버거",
            imageUrl = "https://test.com",
            price = -1,
            additionalPrice = -1,
            categoryId = 1,
            stock = -1,
            hidden = false
        )

        val violations = validator.validate(menuDto)

        assertThat(violations).isNotEmpty
        assertThat(violations.filter {it.constraintDescriptor.annotation is Min }.size).isEqualTo(3)
    }
}
