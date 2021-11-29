package com.baekseju.howmuch.pos.controller

import com.baekseju.howmuch.pos.dto.MenuDto
import com.baekseju.howmuch.pos.service.MenuService
import org.hamcrest.Matchers.hasSize
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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import java.time.Instant

@WebMvcTest(MenuController::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("dev")
internal class MenuControllerTest{
    @Autowired
    private lateinit var mockMvc: MockMvc
    @MockBean
    private lateinit var menuService: MenuService

    private fun <T> any(): T {
        return Mockito.any<T>()
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
            createdAt = Instant.now(),
            updatedAt = Instant.now()
        ))
        menus.add(MenuDto(
            id = 2,
            name = "cola",
            price = 1500,
            additionalPrice = 0,
            categoryId = 103,
            stock = 999,
            hidden = false,
            createdAt = Instant.now(),
            updatedAt = Instant.now()
        ))
    }

    @Test
    fun getMenuList(){
        //given
        given(menuService.getMenuList()).willReturn(menus)

        //when, then
        mockMvc.perform(get("/api/menus"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$", hasSize<Array<Any>>(2)))
            .andExpect(jsonPath("$[0].id").exists())
            .andExpect(jsonPath("$[0].name").exists())
            .andExpect(jsonPath("$[0].price").exists())
            .andExpect(jsonPath("$[0].additionalPrice").exists())
            .andExpect(jsonPath("$[0].stock").exists())
            .andExpect(jsonPath("$[0].categoryId").doesNotExist())
            .andExpect(jsonPath("$[0].hidden").doesNotExist())
            .andExpect(jsonPath("$[0].createdAt").doesNotExist())
            .andExpect(jsonPath("$[0].updatedAt").doesNotExist())
            .andExpect(jsonPath("$[0].deletedAt").doesNotExist())

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
            .andExpect(jsonPath("$.name").exists())
            .andExpect(jsonPath("$.price").exists())
            .andExpect(jsonPath("$.additionalPrice").exists())
            .andExpect(jsonPath("$.stock").exists())
            .andExpect(jsonPath("$.categoryId").exists())
            .andExpect(jsonPath("$.hidden").doesNotExist())
            .andExpect(jsonPath("$.createdAt").exists())
            .andExpect(jsonPath("$.updatedAt").exists())
            .andExpect(jsonPath("$.deletedAt").doesNotExist())

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
                createdAt = Instant.now(),
                updatedAt = Instant.now()
            )
        }

        //when, then
        mockMvc.perform(
            post("/api/menus")
                .contentType(MediaType  .APPLICATION_JSON)
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
            .andExpect(status().isOk)
            .andDo(print())

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

