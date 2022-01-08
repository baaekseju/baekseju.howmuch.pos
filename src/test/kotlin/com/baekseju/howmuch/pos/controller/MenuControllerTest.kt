package com.baekseju.howmuch.pos.controller

import com.baekseju.howmuch.pos.dto.MenuDto
import com.baekseju.howmuch.pos.service.MenuService
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.eq
import org.mockito.BDDMockito
import org.mockito.BDDMockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.time.Instant
import javax.persistence.EntityNotFoundException

@WebMvcTest(MenuController::class)
@ActiveProfiles("dev")
internal class MenuControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var menuService: MenuService

    private fun <T> any(): T {
        return BDDMockito.any()
    }

    private val menuDtos = mutableListOf<MenuDto>()

    @BeforeEach
    fun setup() {
        setMenuDtos()
    }

    private fun setMenuDtos() {
        menuDtos.add(
            MenuDto(
                id = 1,
                name = "hamburger",
                price = 5000,
                imageUrl = "https://via.placeholder.com/200x200",
                additionalPrice = 500,
                categoryId = 100,
                stock = 30,
                hidden = false,
                createdAt = Instant.now(),
                updatedAt = Instant.now()
            )
        )
        menuDtos.add(
            MenuDto(
                id = 2,
                name = "cola",
                price = 1500,
                imageUrl = "https://via.placeholder.com/200x200",
                additionalPrice = 0,
                categoryId = 103,
                stock = 999,
                hidden = false,
                createdAt = Instant.now(),
                updatedAt = Instant.now()
            )
        )
        menuDtos.add(
            MenuDto(
                id = 3,
                name = "chips",
                price = 2000,
                imageUrl = "https://via.placeholder.com/200x200",
                additionalPrice = 0,
                categoryId = 102,
                stock = 500,
                hidden = true,
                createdAt = Instant.now(),
                updatedAt = Instant.now()
            )
        )
    }

    @Test
    fun getMenusHiddenIsFalse() {
        //given
        given(menuService.getMenus(false)).willReturn(menuDtos.filter { !it.hidden })

        //when, then
        mockMvc.perform(get("/api/menus"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$", hasSize<Array<Any>>(2)))
            .andExpect(jsonPath("$[0].id").exists())
            .andExpect(jsonPath("$[0].name").exists())
            .andExpect(jsonPath("$[0].price").exists())
            .andExpect(jsonPath("$[0].imageUrl").exists())
            .andExpect(jsonPath("$[0].additionalPrice").exists())
            .andExpect(jsonPath("$[0].stock").exists())
            .andExpect(jsonPath("$[0].categoryId").doesNotExist())
            .andExpect(jsonPath("$[0].hidden").doesNotExist())
            .andExpect(jsonPath("$[0].createdAt").doesNotExist())
            .andExpect(jsonPath("$[0].updatedAt").doesNotExist())
            .andExpect(jsonPath("$[0].deletedAt").doesNotExist())

        then(menuService).should().getMenus(false)
    }

    @Test
    fun getMenusHiddenIsTrue() {
        //given
        given(menuService.getMenus(true)).willReturn(menuDtos.filter { it.hidden })

        //when, then
        mockMvc.perform(get("/api/menus?hidden=true"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$", hasSize<Array<Any>>(1)))

        then(menuService).should().getMenus(true)
    }

    @Test
    fun getExistedMenu() {
        //given
        val id = 1
        given(menuService.getMenu(id)).willReturn(menuDtos.first { it.id == id })

        //when, then
        mockMvc.perform(get("/api/menus/$id"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value("$id"))
            .andExpect(jsonPath("$.name").exists())
            .andExpect(jsonPath("$.price").exists())
            .andExpect(jsonPath("$.imageUrl").exists())
            .andExpect(jsonPath("$.additionalPrice").exists())
            .andExpect(jsonPath("$.stock").exists())
            .andExpect(jsonPath("$.categoryId").exists())
            .andExpect(jsonPath("$.hidden").value(false))
            .andExpect(jsonPath("$.createdAt").exists())
            .andExpect(jsonPath("$.updatedAt").exists())
            .andExpect(jsonPath("$.deletedAt").doesNotExist())

        then(menuService).should().getMenu(id)
    }

    @Test
    fun getNotExistedMenuDetail() {
        //given
        val id = 999
        val errorMsg = "menu entity not found"
        given(menuService.getMenu(id)).willThrow(EntityNotFoundException(errorMsg))

        //when, then
        mockMvc.perform(get("/api/menus/$id"))
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("$.timeStamp").exists())
            .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()))
            .andExpect(jsonPath("$.error").value(HttpStatus.NOT_FOUND.reasonPhrase))
            .andExpect(jsonPath("$.path").value("/api/menus/$id"))
            .andExpect(jsonPath("$.message").value(errorMsg))

        then(menuService).should().getMenu(id)
    }

    @Test
    fun addMenuWithValidData() {
        //given
        val id = 1
        given(menuService.addMenu(any())).will {
            val menuDto: MenuDto = it.getArgument(0)
            MenuDto(
                id = id,
                name = menuDto.name,
                price = menuDto.price,
                imageUrl = menuDto.imageUrl,
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
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"hamburger\", \"price\": 5000, \"imageUrl\": \"https://via.placeholder.com/200x200\", \"additionalPrice\": 500, \"categoryId\": 1, \"stock\": 100, \"hidden\": false}")
        )
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.id").value(id))
            .andExpect(jsonPath("$.name").value("hamburger"))

        then(menuService).should().addMenu(any())
    }

    @Test
    fun addMenuWithInvalidData() {

    }

    @Test
    fun httpMessageConverterFail() {
        //when, then
        mockMvc.perform(
            post("/api/menus")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"price\": 5000, \"imageUrl\": \"https://via.placeholder.com/200x200\", \"additionalPrice\": 500, \"categoryId\": 1, \"stock\": 100, \"hidden\": false}")
        )
            .andExpect { result -> assertThat(result.resolvedException).isInstanceOf(HttpMessageNotReadableException::class.java) }
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.timeStamp").exists())
            .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
            .andExpect(jsonPath("$.error").value(HttpStatus.BAD_REQUEST.reasonPhrase))
            .andExpect(jsonPath("$.path").value("/api/menus"))
            .andExpect(jsonPath("$.message").exists())
    }

    @Test
    fun putExistMenu() {
        val id = 1
        given(menuService.updateMenu(eq(id), any())).will {
            val menuDto: MenuDto = it.getArgument(1)
            MenuDto(
                id = id,
                name = menuDto.name,
                price = menuDto.price,
                imageUrl = menuDto.imageUrl,
                additionalPrice = menuDto.additionalPrice,
                categoryId = menuDto.categoryId,
                stock = menuDto.stock,
                hidden = menuDto.hidden,
                createdAt = Instant.now(),
                updatedAt = Instant.now()
            )
        }

        mockMvc.perform(
            put("/api/menus/$id")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"hamburger\", \"price\": 10000, \"imageUrl\": \"https://via.placeholder.com/200x200\", \"additionalPrice\": 1000, \"categoryId\": 1, \"stock\": 500, \"hidden\": false}")
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(id))

        then(menuService).should().updateMenu(eq(id), any())
    }

    @Test
    fun putNotExistMenu() {
        val id = 999
        given(menuService.updateMenu(eq(id), any())).willThrow(EntityNotFoundException())

        mockMvc.perform(
            put("/api/menus/$id")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"hamburger\", \"price\": 10000, \"imageUrl\": \"https://via.placeholder.com/200x200\", \"additionalPrice\": 1000, \"categoryId\": 1, \"stock\": 500, \"hidden\": false}")
        )
            .andExpect(status().isNotFound)

        then(menuService).should().updateMenu(eq(id), any())
    }

    @Test
    fun putMenuWithInvalidData() {

    }

    @Test
    fun softDeleteMenu() {
        val id = 1

        mockMvc.perform(delete("/api/menus/$id"))
            .andExpect(status().isOk)

        then(menuService).should().deleteMenu(id, false)
    }

    @Test
    fun forceDeleteMenu() {
        val id = 1

        mockMvc.perform(delete("/api/menus/$id?force=true"))
            .andExpect(status().isOk)

        then(menuService).should().deleteMenu(id, true)
    }

    @Test
    fun deleteNotExistMenu() {
        val id = 999
        given(menuService.deleteMenu(id, false)).willThrow(EntityNotFoundException())

        mockMvc.perform(delete("/api/menus/$id"))
            .andExpect(status().isNotFound)

        then(menuService).should().deleteMenu(id, false)
    }
}
