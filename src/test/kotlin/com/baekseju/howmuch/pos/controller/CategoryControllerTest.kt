package com.baekseju.howmuch.pos.controller

import com.baekseju.howmuch.pos.dto.MenuDto
import com.baekseju.howmuch.pos.service.CategoryService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.eq
import org.mockito.BDDMockito
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.Instant

@WebMvcTest(CategoryController::class)
internal class CategoryControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var categoryService: CategoryService

    private fun <T> any(): T {
        return BDDMockito.any()
    }

    private val burgerMenuDtos = mutableListOf<MenuDto>()

    @BeforeEach
    fun setup() {
        setBurgerMenuDtos()
    }

    private fun setBurgerMenuDtos() {
        burgerMenuDtos.add(
            MenuDto(
                id = 1,
                name = "hamburger",
                price = 5000,
                imageUrl = "https://via.placeholder.com/200x200",
                additionalPrice = 0,
                categoryId = 1,
                stock = 30,
                hidden = false,
                createdAt = Instant.now(),
                updatedAt = Instant.now()
            )
        )
        burgerMenuDtos.add(
            MenuDto(
                id = 2,
                name = "bulgogiburger",
                price = 3500,
                imageUrl = "https://via.placeholder.com/200x200",
                additionalPrice = 0,
                categoryId = 1,
                stock = 999,
                hidden = false,
                createdAt = Instant.now(),
                updatedAt = Instant.now()
            )
        )
        burgerMenuDtos.add(
            MenuDto(
                id = 3,
                name = "hotburger",
                price = 6000,
                imageUrl = "https://via.placeholder.com/200x200",
                additionalPrice = 0,
                categoryId = 1,
                stock = 500,
                hidden = false,
                createdAt = Instant.now(),
                updatedAt = Instant.now()
            )
        )
    }

    @Test
    fun getMenusByCategory() {
        //given
        val pageRequest = PageRequest.of(0, 16)
        val pageMenuDto = PageImpl(burgerMenuDtos, pageRequest, burgerMenuDtos.size.toLong())
        val categoryId = 1
        given(categoryService.getMenusByCategory(eq(categoryId), any())).willReturn(pageMenuDto)

        mockMvc.perform(get("/api/categories/$categoryId/menus?page=0&size=16"))
            .andExpect(status().isOk)

        then(categoryService).should().getMenusByCategory(eq(categoryId), any())
    }
}
