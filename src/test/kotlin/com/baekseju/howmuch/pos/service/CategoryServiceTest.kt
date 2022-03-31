package com.baekseju.howmuch.pos.service

import com.baekseju.howmuch.pos.entity.Menu
import com.baekseju.howmuch.pos.repository.MenuRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
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
                name = "hamburger",
                price = 5000,
                imageUrl = "https://via.placeholder.com/200x200",
                additionalPrice = 0,
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
                name = "hotburger",
                price = 6000,
                imageUrl = "https://via.placeholder.com/200x200",
                additionalPrice = 1000,
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
        val pageMenuDto = PageImpl(menus, pageRequest, menus.size.toLong())
        given(menuRepository.findAllByCategoryIdOrderById(categoryId, pageRequest)).willReturn(pageMenuDto)

        categoryService.getMenusByCategory(categoryId, pageRequest)

        then(menuRepository).should().findAllByCategoryIdOrderById(categoryId, pageRequest)
    }
}
