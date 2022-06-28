package com.baekseju.howmuch.pos.repository

import com.baekseju.howmuch.pos.entity.User
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import javax.validation.ConstraintViolationException

@SpringBootTest
internal class UserRepositoryTest {
    @Autowired
    private lateinit var userRepository: UserRepository

    @AfterEach
    fun initDB() {
        userRepository.deleteAll()
    }

    @Test
    fun saveInvalidData() {
        assertThatThrownBy { userRepository.save(User(
            phoneNumber = "010-12-123"
        )) }.isInstanceOf(ConstraintViolationException::class.java)
    }
}
