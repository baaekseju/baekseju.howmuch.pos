package com.baekseju.howmuch.pos.controller

import com.baekseju.howmuch.pos.dto.CategoryDto
import com.baekseju.howmuch.pos.dto.MenuDto
import com.baekseju.howmuch.pos.service.CategoryService
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.eq
import org.mockito.BDDMockito
import org.mockito.BDDMockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.web.bind.MethodArgumentNotValidException
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
    private val categoryDtos = mutableListOf<CategoryDto>()

    @BeforeEach
    fun setup() {
        setCategoryDtos()
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

    private fun setCategoryDtos() {
        categoryDtos.add(
            CategoryDto(
                id = 1,
                name = "burger",
                createdAt = Instant.now(),
                updatedAt = Instant.now()
            )
        )
        categoryDtos.add(
            CategoryDto(
                id = 2,
                name = "drink",
                createdAt = Instant.now(),
                updatedAt = Instant.now()
            )
        )
        categoryDtos.add(
            CategoryDto(
                id = 3,
                name = "side",
                createdAt = Instant.now(),
                updatedAt = Instant.now()
            )
        )
    }

    @Test
    fun getCategory() {
        given(categoryService.getCategories()).willReturn(categoryDtos)

        mockMvc.perform(get("/api/categories"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$", hasSize<Array<Any>>(3)))
            .andExpect(jsonPath("$[0].id").exists())
            .andExpect(jsonPath("$[0].name").exists())
            .andExpect(jsonPath("$[0].createdAt").exists())
            .andExpect(jsonPath("$[0].updatedAt").exists())

        then(categoryService).should().getCategories()
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

    @Test
    fun addCategory() {
        val id = 1
        given(categoryService.addCategory(any())).will {
            val categoryDto: CategoryDto = it.getArgument(0)
            CategoryDto(
                id = id,
                name = categoryDto.name,
                createdAt = Instant.now(),
                updatedAt = Instant.now()
            )
        }

        mockMvc.perform(
            post("/api/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"set-menu\"}")

        )
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.id").value(id))
            .andExpect(jsonPath("$.name").value("set-menu"))

        then(categoryService).should().addCategory(any())
    }

    @Test
    fun addCategoryWithInvalidData() {
        mockMvc.perform(
            post("/api/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\" \"}")

        )
            .andExpect { result -> assertThat(result.resolvedException)
                .isInstanceOf(MethodArgumentNotValidException::class.java) }
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.timeStamp").exists())
            .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
            .andExpect(jsonPath("$.error").value(HttpStatus.BAD_REQUEST.reasonPhrase))
            .andExpect(jsonPath("$.path").value("/api/categories"))
            .andExpect(jsonPath("$.messages", hasItem("한 글자 이상 입력해야 합니다.")))
    }

    @Test
    fun putCategory() {
        val id = 1
        given(categoryService.updateCategory(eq(id), any())).will {
            val categoryDto: CategoryDto = it.getArgument(1)
            CategoryDto(
                id = id,
                name = categoryDto.name,
                createdAt = Instant.now(),
                updatedAt = Instant.now()
            )
        }

        mockMvc.perform(
            put("/api/categories/$id")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"set-menu\"}")

        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(id))
            .andExpect(jsonPath("$.name").value("set-menu"))

        then(categoryService).should().updateCategory(eq(id), any())
    }

    @Test
    fun putCategoryWithInvalidData() {
        val id = 1

        mockMvc.perform(
            put("/api/categories/$id")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\" \"}")

        )
            .andExpect { result -> assertThat(result.resolvedException)
                .isInstanceOf(MethodArgumentNotValidException::class.java) }
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.timeStamp").exists())
            .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
            .andExpect(jsonPath("$.error").value(HttpStatus.BAD_REQUEST.reasonPhrase))
            .andExpect(jsonPath("$.path").value("/api/categories/$id"))
            .andExpect(jsonPath("$.messages", hasItem("한 글자 이상 입력해야 합니다.")))
    }

    @Test
    fun deleteCategory() {
        val id = 1
        given(categoryService.deleteCategory(id)).willReturn("force delete success")

        mockMvc.perform(delete("/api/categories/$id"))
            .andExpect(status().isOk)

        then(categoryService).should().deleteCategory(id)
    }
}
