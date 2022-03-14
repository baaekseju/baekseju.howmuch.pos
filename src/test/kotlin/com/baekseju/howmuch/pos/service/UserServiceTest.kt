package com.baekseju.howmuch.pos.service

import com.baekseju.howmuch.pos.dto.PointDto
import com.baekseju.howmuch.pos.dto.UserDto
import com.baekseju.howmuch.pos.entity.Point
import com.baekseju.howmuch.pos.entity.User
import com.baekseju.howmuch.pos.exception.UserExistException
import com.baekseju.howmuch.pos.repository.PointRepository
import com.baekseju.howmuch.pos.repository.UserRepository
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito
import org.mockito.BDDMockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import java.time.Instant
import javax.persistence.EntityNotFoundException

@SpringBootTest
internal class UserServiceTest {

    @Autowired
    private lateinit var userService: UserService

    @MockBean
    private lateinit var userRepository: UserRepository

    @MockBean
    private lateinit var pointRepository: PointRepository

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

    @Test
    fun getNotExistUserByPhoneNumber() {
        val phoneNumber = "010-9999-9999"
        given(userRepository.findByPhoneNumber(phoneNumber)).willReturn(null)

        assertThatThrownBy { userService.getUserByPhoneNumber(phoneNumber) }
            .isInstanceOf(EntityNotFoundException::class.java)
        then(userRepository).should().findByPhoneNumber(phoneNumber)
    }

    @Test
    fun addUser() {
        val userId = 1
        given(userRepository.findByPhoneNumber(any())).willReturn(null)
        given(userRepository.save(any())).will {
            val user: User = it.getArgument(0)
            User(
                id = userId,
                phoneNumber = user.phoneNumber,
                createdAt = Instant.now(),
                updatedAt = Instant.now()
            )
        }
        val mockUserDto = UserDto(phoneNumber = "010-1111-2222")

        val userDto = userService.addUser(mockUserDto)

        then(userRepository).should().findByPhoneNumber(any())
        then(userRepository).should().save(any())
        then(pointRepository).should().save(any())
        assertThat(userDto.id).isEqualTo(userId)
    }

    @Test
    fun addNotExistUser() {
        val phoneNumber = "010-1111-2222"
        given(userRepository.findByPhoneNumber(phoneNumber)).willReturn(users.first { it.phoneNumber == phoneNumber })
        val mockUserDto = UserDto(phoneNumber = "010-1111-2222")

        assertThatThrownBy { userService.addUser(mockUserDto) }
            .isInstanceOf(UserExistException::class.java)
        then(userRepository).should().findByPhoneNumber(phoneNumber)
        then(userRepository).should(never()).save(any())
        then(pointRepository).should(never()).save(any())
    }

    @Test
    fun getPointByUser() {
        val userId = 1
        val pointId = 1
        given(pointRepository.findByUserId(userId)).willReturn(
            Point(
                id = pointId,
                point = 1000,
                user = User(id = userId, phoneNumber = "010-1111-2222"),
                createdAt = Instant.now(),
                updatedAt = Instant.now()
            )
        )

        val pointDto = userService.getPointByUser(userId)

        then(pointRepository).should().findByUserId(userId)
        assertThat(pointDto.point).isEqualTo(1000)
    }

    @Test
    fun getPointByNotExistUser() {
        val userId = 1
        given(pointRepository.findByUserId(userId)).willReturn(null)

        assertThatThrownBy { userService.getPointByUser(userId) }
            .isInstanceOf(EntityNotFoundException::class.java)

        then(pointRepository).should().findByUserId(userId)
    }

    @Test
    fun addPoint() {
        val userId = 1
        val prevPoint = 1000
        val willAddPoint = 500
        val mockPointDto = PointDto(point = willAddPoint)

        val pointDto = userService.addPoint(userId, mockPointDto)

        then(pointRepository).should().findByUserId(userId)
        then(pointRepository).should().save(any())
        assertThat(pointDto.point).isEqualTo(prevPoint + willAddPoint)
    }
}
