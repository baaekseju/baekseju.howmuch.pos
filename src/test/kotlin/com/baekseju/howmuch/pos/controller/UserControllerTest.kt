package com.baekseju.howmuch.pos.controller

import com.baekseju.howmuch.pos.dto.PointDto
import com.baekseju.howmuch.pos.dto.UserDto
import com.baekseju.howmuch.pos.exception.UserExistException
import com.baekseju.howmuch.pos.service.UserService
import org.assertj.core.api.Assertions
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.eq
import org.mockito.BDDMockito
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.web.bind.MethodArgumentNotValidException
import java.time.Instant
import javax.persistence.EntityNotFoundException
import javax.validation.ConstraintViolationException

@WebMvcTest(UserController::class)
internal class UserControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var userService: UserService

    private fun <T> any(): T {
        return BDDMockito.any()
    }

    private val userDtos = mutableListOf<UserDto>()

    @BeforeEach
    fun setup() {
        setUserDtos()
    }

    private fun setUserDtos() {
        userDtos.add(
            UserDto(
                phoneNumber = "010-1111-2222"
            )
        )
        userDtos.add(
            UserDto(
                phoneNumber = "010-2222-3333"
            )
        )
        userDtos.add(
            UserDto(
                phoneNumber = "010-3333-4444"
            )
        )
        userDtos.add(
            UserDto(
                phoneNumber = "010-4444-5555"
            )
        )
    }

    @Test
    fun getUserByPhoneNumber() {
        val phoneNumber = "010-1111-2222"
        given(userService.getUserByPhoneNumber(any())).willReturn(userDtos.first { it.phoneNumber == phoneNumber })

        mockMvc.perform(get("/api/users?phone-number=${phoneNumber}"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
            .andExpect(jsonPath("$.data.phoneNumber").value(phoneNumber))

        then(userService).should().getUserByPhoneNumber(any())
    }

    @Test
    fun getNotExistUserByPhoneNumber() {
        val phoneNumber = "010-9999-9999"
        val errorMsg = "존재하지 않은 번호입니다."
        given(userService.getUserByPhoneNumber(phoneNumber)).willThrow(EntityNotFoundException(errorMsg))

        mockMvc.perform(get("/api/users?phone-number=${phoneNumber}"))
            .andExpect { result ->
                Assertions.assertThat(result.resolvedException)
                    .isInstanceOf(EntityNotFoundException::class.java)
            }
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()))
            .andExpect(jsonPath("$.error").value(HttpStatus.NOT_FOUND.reasonPhrase))
            .andExpect(jsonPath("$.path").value("/api/users"))
            .andExpect(jsonPath("$.messages[0]").value(errorMsg))

        then(userService).should().getUserByPhoneNumber(any())
    }

    @Test
    fun getUserByInvalidPhoneNumber() {
        val phoneNumber = "010-111-2222"
        mockMvc.perform(get("/api/users?phone-number=${phoneNumber}"))
            .andExpect { result ->
                Assertions.assertThat(result.resolvedException)
                    .isInstanceOf(ConstraintViolationException::class.java)
            }
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
            .andExpect(jsonPath("$.error").value(HttpStatus.BAD_REQUEST.reasonPhrase))
            .andExpect(jsonPath("$.path").value("/api/users"))
            .andExpect(jsonPath("$.messages", hasSize<String>(greaterThanOrEqualTo(1))))
    }

    @Test
    fun addUser() {
        val userId = 1
        val phoneNumber = "010-1111-2222"
        given(userService.addUser(any())).will {
            val userDto: UserDto = it.getArgument(0)
            UserDto(
                id = userId,
                phoneNumber = userDto.phoneNumber,
                createdAt = Instant.now(),
                updatedAt = Instant.now()
            )
        }

        mockMvc.perform(
            post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"phoneNumber\": \"${phoneNumber}\"}")
        )
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.data.id").value(userId))

        then(userService).should().addUser(any())
    }

    @Test
    fun addExistUser() {
        val phoneNumber = "010-1111-2222"
        given(userService.addUser(any())).willThrow(UserExistException(phoneNumber))

        mockMvc.perform(
            post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"phoneNumber\": \"${phoneNumber}\"}")
        )
            .andExpect { result ->
                Assertions.assertThat(result.resolvedException)
                    .isInstanceOf(UserExistException::class.java)
            }
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
            .andExpect(jsonPath("$.error").value(HttpStatus.BAD_REQUEST.reasonPhrase))
            .andExpect(jsonPath("$.path").value("/api/users"))
            .andExpect(jsonPath("$.messages", hasItem("입력하신 ${phoneNumber}는 이미 가입된 번호입니다.")))

        then(userService).should().addUser(any())
    }

    @Test
    fun addUserWithInvalidData() {
        val phoneNumber = "010-111-2222"

        mockMvc.perform(
            post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"phoneNumber\": \"${phoneNumber}\"}")
        )
            .andExpect { result ->
                Assertions.assertThat(result.resolvedException)
                    .isInstanceOf(MethodArgumentNotValidException::class.java)
            }
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
            .andExpect(jsonPath("$.error").value(HttpStatus.BAD_REQUEST.reasonPhrase))
            .andExpect(jsonPath("$.path").value("/api/users"))
            .andExpect(jsonPath("$.messages", hasSize<String>(greaterThanOrEqualTo(1))))
    }

    @Test
    fun getPointByUser() {
        val userId = 1
        val point = 1000
        given(userService.getPointByUser(userId)).willReturn(
            PointDto(
                id = 1,
                point = point,
                createdAt = Instant.now(),
                updatedAt = Instant.now()
            )
        )

        mockMvc.perform(get("/api/users/${userId}/point"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
            .andExpect(jsonPath("$.data.point").value(point))

        then(userService).should().getPointByUser(userId)
    }

    @Test
    fun getPointByNotExistUser() {
        val userId = 1
        given(userService.getPointByUser(userId)).willThrow(EntityNotFoundException("존재하지 않은 사용자입니다."))

        mockMvc.perform(get("/api/users/${userId}/point"))
            .andExpect { result ->
                Assertions.assertThat(result.resolvedException)
                    .isInstanceOf(EntityNotFoundException::class.java)
            }
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()))

        then(userService).should().getPointByUser(userId)
    }

    @Test
    fun patchPointByUser() {
        val userId = 1
        val point = 500
        given(userService.addPoint(eq(userId), eq(point))).willReturn(
            PointDto(
                id = 1,
                point = 1500
            )
        )

        mockMvc.perform(
            patch("/api/users/${userId}/point")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"point\": ${point}}")
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))

        then(userService).should().addPoint(eq(userId), eq(point))
    }

    @Test
    fun patchPointByNotExistUser() {
        val userId = 1
        val point = 500
        given(userService.addPoint(eq(userId), eq(point))).willThrow(EntityNotFoundException())

        mockMvc.perform(
            patch("/api/users/${userId}/point")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"point\": ${point}}")
        )
            .andExpect { result ->
                Assertions.assertThat(result.resolvedException)
                    .isInstanceOf(EntityNotFoundException::class.java)
            }
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()))

        then(userService).should().addPoint(eq(userId), eq(point))
    }

    @Test
    fun patchPointByUserWithInvalidData() {
        val userId = 1
        val point = 0

        mockMvc.perform(
            patch("/api/users/${userId}/point")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"point\": ${point}}")
        )
            .andExpect { result ->
                Assertions.assertThat(result.resolvedException)
                    .isInstanceOf(MethodArgumentNotValidException::class.java)
            }
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
            .andExpect(jsonPath("$.error").value(HttpStatus.BAD_REQUEST.reasonPhrase))
            .andExpect(jsonPath("$.path").value("/api/users/$userId/point"))
            .andExpect(jsonPath("$.messages", hasSize<String>(greaterThanOrEqualTo(1))))
    }
}
