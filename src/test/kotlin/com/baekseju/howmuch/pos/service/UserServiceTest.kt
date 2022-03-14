package com.baekseju.howmuch.pos.service

import com.baekseju.howmuch.pos.entity.User
import com.baekseju.howmuch.pos.repository.UserRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean

@SpringBootTest
internal class UserServiceTest {

    @Autowired
    private lateinit var userService: UserService

    @MockBean
    private lateinit var userRepository: UserRepository

    private val users = mutableListOf<User>()

    private fun <T> any(): T {
        return BDDMockito.any()
    }

    @BeforeEach
    fun setup() {
        setUsers()
    }

    private fun setUsers() {
        users.add(
            User(
                phoneNumber = "010-1111-2222"
            )
        )
        users.add(
            User(
                phoneNumber = "010-2222-3333"
            )
        )
        users.add(
            User(
                phoneNumber = "010-3333-4444"
            )
        )
        users.add(
            User(
                phoneNumber = "010-4444-5555"
            )
        )
    }

    @Test
    fun getUserByPhoneNumber() {
        val phoneNumber = "010-1111-2222"
        given(userRepository.findByPhoneNumber(phoneNumber)).willReturn(users.first { it.phoneNumber == phoneNumber })

        val userDto = userService.getUserByPhoneNumber(phoneNumber)

        then(userRepository).should().findByPhoneNumber(phoneNumber)
        assertThat(userDto.phoneNumber).isEqualTo(phoneNumber)
    }
}
