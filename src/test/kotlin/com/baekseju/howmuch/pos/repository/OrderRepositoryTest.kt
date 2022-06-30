package com.baekseju.howmuch.pos.repository

import com.baekseju.howmuch.pos.entity.Order
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import javax.validation.ConstraintViolationException

@SpringBootTest
internal class OrderRepositoryTest {
    @Autowired
    private lateinit var orderRepository: OrderRepository

    @AfterEach
    fun initDB() {
        orderRepository.deleteAll()
    }

    @Test
    fun saveInvalidData() {
        assertThatThrownBy { orderRepository.save(Order(
            totalPrice = 500,
            payWith = ""
        )) }.isInstanceOf(ConstraintViolationException::class.java)
    }
}
