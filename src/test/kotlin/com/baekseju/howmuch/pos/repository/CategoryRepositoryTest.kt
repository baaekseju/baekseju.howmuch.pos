package com.baekseju.howmuch.pos.repository

import com.baekseju.howmuch.pos.entity.Category
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import javax.validation.ConstraintViolationException

@SpringBootTest
internal class CategoryRepositoryTest {
    @Autowired
    private lateinit var categoryRepository: CategoryRepository

    @AfterEach
    fun initDB() {
        categoryRepository.deleteAll()
    }

    @Test
    fun saveInvalidData() {
        assertThatThrownBy {
            categoryRepository.save(
                Category(
                    name = null
                )
            )
        }.isInstanceOf(ConstraintViolationException::class.java)
    }

    @Test
    fun updateInvalidData() {
        val category = Category(name = "햄버거")
        val savedCategory = categoryRepository.save(category)

        savedCategory.name = ""

        assertThatThrownBy { categoryRepository.save(savedCategory) }.hasRootCauseInstanceOf(
            ConstraintViolationException::class.java
        )
    }
}
