package com.baekseju.howmuch.pos.controller

import com.baekseju.howmuch.pos.dto.MenuDto
import com.baekseju.howmuch.pos.service.MenuService
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.ArgumentMatchers.eq
import org.mockito.BDDMockito.*
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.time.LocalDateTime

@WebMvcTest(MenuController::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("dev")
internal class MenuControllerTest{
    @Autowired
    private lateinit var mockMvc: MockMvc
    @MockBean
    private lateinit var menuService: MenuService

    private fun <T> any(): T {
        Mockito.any<T>()
        return null as T
    }

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
            additionalPrice = 500,
            categoryId = 100,
            stock = 30,
            hidden = false,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        ))
        menus.add(MenuDto(
            id = 2,
            name = "cola",
            price = 1500,
            additionalPrice = 0,
            categoryId = 103,
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

        then(menuService).should().getMenuList()
    }

    @Test
    fun getExistedMenuDetail(){
        //given
        val id = 1
        given(menuService.getMenuDetail(id)).willReturn(menus[0])

        //when, then
        mockMvc.perform(get("/api/menus/$id"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value("$id"))

        then(menuService).should().getMenuDetail(id)
    }

    @Test
    fun getNotExistedMenuDetail(){

    }

    @Test
    fun postMenuWithValidData(){
        //given
        val id = 1
        given(menuService.addMenu(any())).will {
            val menuDto : MenuDto = it.getArgument(0)
            MenuDto(
                id = id,
                name = menuDto.name,
                price = menuDto.price,
                additionalPrice = menuDto.additionalPrice,
                categoryId = menuDto.categoryId,
                stock = menuDto.stock,
                hidden = menuDto.hidden,
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now()
            )
        }

        //when, then
        mockMvc.perform(
            post("/api/menus")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"hamburger\", \"price\": 5000, \"additionalPrice\": 500, \"categoryId\": 1, \"stock\": 100, \"hidden\": false}"))
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.id").value(id))
            .andExpect(jsonPath("$.name").value("hamburger"))

        then(menuService).should().addMenu(any())
    }

    @Test
    fun postMenuWithInvalidData(){

    }

    @Test
    fun putExistMenu(){
        val id = 1
        mockMvc.perform(
            put("/api/menus/$id")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"hamburger\", \"price\": 10000, \"additionalPrice\": 1000, \"categoryId\": 1, \"stock\": 500, \"hidden\": false}"))
            .andExpect(status().isCreated)

        then(menuService).should().updateMenu(eq(id), any())
    }

    @Test
    fun putNotExistMenu(){

    }

    @Test
    fun putMenuWithInvalidData(){

    }

    @Test
    fun deleteExistMenu(){
        val id = 1
        mockMvc.perform(delete("/api/menus/$id"))
            .andExpect(status().isOk)

        then(menuService).should().softDeleteMenu(id)
    }

    @Test
    fun deleteNotExistMenu(){

    }
}

