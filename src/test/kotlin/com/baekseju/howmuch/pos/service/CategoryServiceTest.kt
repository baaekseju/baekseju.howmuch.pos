package com.baekseju.howmuch.pos.service

import com.baekseju.howmuch.pos.entity.Category
import com.baekseju.howmuch.pos.entity.Menu
import com.baekseju.howmuch.pos.repository.MenuRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import java.time.Instant

@SpringBootTest
internal class CategoryServiceTest {
    @Autowired
    private lateinit var categoryService: CategoryService

    @MockBean
    private lateinit var menuRepository: MenuRepository

    private val menus = mutableListOf<Menu>()

    @BeforeEach
    fun setup() {
        setMenus()
    }

    private fun setMenus() {
        menus.add(
            Menu(
                id = 1,
                name = null,
                price = 5000,
                imageUrl = "https://via.placeholder.com/200x200",
                additionalPrice = 0,
                category = Category(1, "햄버거", Instant.now(), Instant.now()),
                stock = 50,
                hidden = false,
                createdAt = Instant.now(),
                updatedAt = Instant.now()
            )
        )
        menus.add(
            Menu(
                id = 2,
                name = "bugogiburger",
                price = 3500,
                imageUrl = "https://via.placeholder.com/200x200",
                additionalPrice = 0,
                category = Category(1, "햄버거", Instant.now(), Instant.now()),
                stock = 999,
                hidden = false,
                createdAt = Instant.now(),
                updatedAt = Instant.now(),
                deletedAt = Instant.now()
            )
        )
        menus.add(
            Menu(
                id = 3,
                name = "cola",
                price = 2000,
                imageUrl = "https://via.placeholder.com/200x200",
                additionalPrice = 0,
                category = Category(2, "음료", Instant.now(), Instant.now()),
                stock = 10,
                hidden = true,
                createdAt = Instant.now(),
                updatedAt = Instant.now()
            )
        )
    }

//    private fun <T> any(): T {
//        return BDDMockito.any()
//    }

    @Test
    fun getMenusByCategory() {
        val categoryId = 1
        val pageRequest = PageRequest.of(0, 16)
        val pageMenuDto = PageImpl(menus.filter { it.category?.id == categoryId }, pageRequest, menus.size.toLong())
        given(menuRepository.findAllByCategoryIdOrderById(categoryId, pageRequest)).willReturn(pageMenuDto)

        val menuDto = categoryService.getMenusByCategory(categoryId, pageRequest)

        then(menuRepository).should().findAllByCategoryIdOrderById(categoryId, pageRequest)
        assertThat(menuDto.numberOfElements).isEqualTo(2)
    }
}
