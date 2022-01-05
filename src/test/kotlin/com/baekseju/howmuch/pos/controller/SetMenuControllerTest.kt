package com.baekseju.howmuch.pos.controller

import com.baekseju.howmuch.pos.dto.SetMenuDto
import com.baekseju.howmuch.pos.service.SetMenuService
import org.hamcrest.Matchers
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.Instant

@WebMvcTest(SetMenuController::class)
@ActiveProfiles("dev")
internal class SetMenuControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var setMenuService: SetMenuService
    private val setMenuDtos = mutableListOf<SetMenuDto>()

    private fun <T> any(): T {
        return Mockito.any()
    }

    @BeforeEach
    fun setup() {
        setSetMenuDtos()
    }

    private fun setSetMenuDtos() {
        setMenuDtos.add(
            SetMenuDto(
                id = 1,
                name = "hamburger set",
                price = 9900,
                imageUrl = "https://via.placeholder.com/200x200",
                hidden = false,
                createdAt = Instant.now(),
                updatedAt = Instant.now()
            )
        )
        setMenuDtos.add(
            SetMenuDto(
                id = 2,
                name = "chicken set",
                price = 12900,
                imageUrl = "https://via.placeholder.com/200x200",
                hidden = false,
                createdAt = Instant.now(),
                updatedAt = Instant.now()
            )
        )
        setMenuDtos.add(
            SetMenuDto(
                id = 3,
                name = "double hamburger set",
                price = 19900,
                imageUrl = "https://via.placeholder.com/200x200",
                hidden = true,
                createdAt = Instant.now(),
                updatedAt = Instant.now()
            )
        )
    }

    @Test
    fun getMenusHiddenIsFalse() {
        given(setMenuService.getSetMenus(false)).willReturn(setMenuDtos.filter { !it.hidden })

        mockMvc.perform(get("/api/setmenus"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$", Matchers.hasSize<Array<Any>>(2)))

        then(setMenuService).should().getSetMenus(false)
    }

    @Test
    fun getMenusHiddenIsTrue() {
        given(setMenuService.getSetMenus(true)).willReturn(setMenuDtos.filter { it.hidden })

        mockMvc.perform(get("/api/setmenus?hidden=true"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$", Matchers.hasSize<Array<Any>>(1)))

        then(setMenuService).should().getSetMenus(true)
    }
}
