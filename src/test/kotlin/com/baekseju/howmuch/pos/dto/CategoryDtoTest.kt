package com.baekseju.howmuch.pos.dto

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import javax.validation.Validation
import javax.validation.Validator
import javax.validation.ValidatorFactory
import javax.validation.constraints.NotBlank


internal class CategoryDtoTest {
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
    fun categoryDto() {
        val categoryDto = CategoryDto(name = "햄버거")

        val violations = validator.validate(categoryDto)

        assertThat(violations).isEmpty()
    }

    @Test
    fun categoryDtoCheckNull() {
        val categoryDto = CategoryDto(name = null)
        val violations = validator.validate(categoryDto)

        assertThat(violations).isNotEmpty
        assertThat(violations.filter { it.constraintDescriptor.annotation is NotBlank }.size).isEqualTo(1)
    }

    @Test
    fun categoryDtoCheckEmpty() {
        val categoryDto = CategoryDto(name = "")
        val violations = validator.validate(categoryDto)

        assertThat(violations).isNotEmpty
        assertThat(violations.filter { it.constraintDescriptor.annotation is NotBlank }.size).isEqualTo(1)
    }

    @Test
    fun categoryDtoCheckBlank() {
        val categoryDto = CategoryDto(name = " ")
        val violations = validator.validate(categoryDto)

        assertThat(violations).isNotEmpty
        assertThat(violations.filter { it.constraintDescriptor.annotation is NotBlank }.size).isEqualTo(1)
    }
}
