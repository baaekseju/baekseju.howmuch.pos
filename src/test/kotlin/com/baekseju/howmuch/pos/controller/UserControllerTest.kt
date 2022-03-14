package com.baekseju.howmuch.pos.controller

import com.baekseju.howmuch.pos.dto.UserDto
import com.baekseju.howmuch.pos.exception.UserExistException
import com.baekseju.howmuch.pos.service.UserService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.filter.CharacterEncodingFilter
import java.time.Instant
import javax.persistence.EntityNotFoundException

@WebMvcTest(UserController::class)
internal class UserControllerTest {
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var ctx: WebApplicationContext

    @MockBean
    private lateinit var userService: UserService

    private fun <T> any(): T {
        return BDDMockito.any()
    }

    private val userDtos = mutableListOf<UserDto>()

    @BeforeEach
    fun setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
            .addFilter<DefaultMockMvcBuilder?>(CharacterEncodingFilter("UTF-8", true))
            .build()

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

        mockMvc.perform(get("/api/users?phoneNumber=${phoneNumber}"))
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

        mockMvc.perform(get("/api/users?phoneNumber=${phoneNumber}"))
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()))
            .andExpect(jsonPath("$.error").value(HttpStatus.NOT_FOUND.reasonPhrase))
            .andExpect(jsonPath("$.path").value("/api/users"))
            .andExpect(jsonPath("$.message").value(errorMsg))

        then(userService).should().getUserByPhoneNumber(any())
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
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
            .andExpect(jsonPath("$.error").value(HttpStatus.BAD_REQUEST.reasonPhrase))
            .andExpect(jsonPath("$.path").value("/api/users"))
            .andExpect(jsonPath("$.message").value("입력하신 ${phoneNumber}는 이미 가입된 번호입니다."))

        then(userService).should().addUser(any())
    }
}
