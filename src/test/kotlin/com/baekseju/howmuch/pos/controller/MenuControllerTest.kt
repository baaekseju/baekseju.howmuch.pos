package com.baekseju.howmuch.pos.controller

import com.baekseju.howmuch.pos.dto.MenuDto
import com.baekseju.howmuch.pos.service.MenuService
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.time.LocalDateTime

@WebMvcTest(MenuController::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class MenuControllerTest(){
    @Autowired
    private lateinit var mockMvc: MockMvc
    @MockBean
    private lateinit var menuService: MenuService

    private val menus = ArrayList<MenuDto>()

    @BeforeAll
    fun setup(){
        setMenus()
    }

    private fun setMenus(){
        menus.add(MenuDto(
            id = 1,
            name = "hamburger",
            price = 5000,
            additional_price = 500,
            category_id = 100,
            stock = 30,
            hidden = false,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        ))
        menus.add(MenuDto(
            id = 2,
            name = "cola",
            price = 1500,
            additional_price = 0,
            category_id = 103,
            stock = 999,
            hidden = false,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        ))
    }

    @Test
    fun getMenuList(){
        //given
        given(menuService.getMenuList()).willReturn(menus)

        //when, then
        mockMvc.perform(get("/api/menus"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].id").value("1"))
            .andExpect(jsonPath("$[1].id").value("2"))
    }
}

