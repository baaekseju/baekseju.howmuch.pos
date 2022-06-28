package com.baekseju.howmuch.pos.repository

import com.baekseju.howmuch.pos.entity.Point
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import javax.validation.ConstraintViolationException

@SpringBootTest
internal class PointRepositoryTest {
    @Autowired
    private lateinit var pointRepository: PointRepository

    @AfterEach
    fun initDB() {
        pointRepository.deleteAll()
    }

    @Test
    fun saveInvalidData() {
        assertThatThrownBy { pointRepository.save(Point(
            point = 1000,
            user = null
        )) }.isInstanceOf(ConstraintViolationException::class.java)
    }
}
