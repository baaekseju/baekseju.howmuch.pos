package com.baekseju.howmuch.pos.entity

import com.baekseju.howmuch.pos.dto.CategoryDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import javax.validation.Validation
import javax.validation.Validator
import javax.validation.ValidatorFactory
import javax.validation.constraints.NotBlank

internal class CategoryTest {
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
    fun category() {
        val category = Category(
            name = "햄버거"
        )

        val violations = validator.validate(category)

        assertThat(violations).isEmpty()
    }

    @Test
    fun categoryCheckNull() {
        val category = Category(name = null)
        val violations = validator.validate(category)

        assertThat(violations).isNotEmpty
        assertThat(violations.filter { it.constraintDescriptor.annotation is NotBlank }.size).isEqualTo(1)
    }

    @Test
    fun categoryCheckEmpty() {
        val category = CategoryDto(name = "")
        val violations = validator.validate(category)

        assertThat(violations).isNotEmpty
        assertThat(violations.filter { it.constraintDescriptor.annotation is NotBlank }.size).isEqualTo(1)
    }

    @Test
    fun categoryCheckBlank() {
        val category = CategoryDto(name = " ")
        val violations = validator.validate(category)

        assertThat(violations).isNotEmpty
        assertThat(violations.filter { it.constraintDescriptor.annotation is NotBlank }.size).isEqualTo(1)
    }
}
