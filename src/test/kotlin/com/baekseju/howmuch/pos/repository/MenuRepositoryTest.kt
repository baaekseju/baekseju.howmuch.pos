package com.baekseju.howmuch.pos.repository

import com.baekseju.howmuch.pos.entity.Category
import com.baekseju.howmuch.pos.entity.Menu
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("dev")
internal class MenuRepositoryTest {
    @Autowired
    private lateinit var menuRepository: MenuRepository
    @Autowired
    private lateinit var categoryRepository: CategoryRepository

    @AfterEach
    fun initDB() {
        menuRepository.deleteAll()
        categoryRepository.deleteAll()
    }

    @Test
    fun findAll() {
        menuRepository.save(
            Menu(
                name = "hamburger",
                price = 5000,
                imageUrl = "https://via.placeholder.com/200x200",
                additionalPrice = 500,
                stock = 50,
                hidden = false
            )
        )
        menuRepository.save(
            Menu(
                name = "cola",
                price = 1500,
                imageUrl = "https://via.placeholder.com/200x200",
                additionalPrice = 0,
                stock = 999,
                hidden = false,
                deletedAt = null
            )
        )

        //when
        val menus = menuRepository.findAll()

        //then
        assertThat(menus.size).isEqualTo(2)
        assertThat(menus[0].name).isEqualTo("hamburger")
        assertThat(menus[1].name).isEqualTo("cola")
    }

    @Test
    fun save() {
        //given
        val category = categoryRepository.save(Category(
            name = "burger"
        ))
        val menu = Menu(
            name = "hamburger",
            price = 5000,
            imageUrl = "https://via.placeholder.com/200x200",
            additionalPrice = 500,
            category = category,
            stock = 50,
            hidden = false
        )

        //when
        val savedMenu = menuRepository.save(menu)

        //then
        assertThat(savedMenu.id).isNotNull
        assertThat(savedMenu.category?.id).isEqualTo(category.id)
        assertThat(savedMenu.createdAt).isNotNull
        assertThat(savedMenu.updatedAt).isNotNull
    }

    @Test
    fun update() {
        //given
        val category = categoryRepository.save(Category(
            name = "burger"
        ))
        val menu = Menu(
            name = "hamburger",
            price = 5000,
            imageUrl = "https://via.placeholder.com/200x200",
            additionalPrice = 500,
            category = category,
            stock = 50,
            hidden = false
        )
        val savedMenu = menuRepository.save(menu)

        // when
        savedMenu.name = "cola"
        val updatedMenu = menuRepository.save(savedMenu)

        //then
        assertThat(updatedMenu.name).isEqualTo("cola")
        assertThat(updatedMenu.updatedAt).isNotEqualTo(savedMenu.updatedAt)
    }

    @Test
    fun softDelete() {
        //given
        val category = categoryRepository.save(Category(
            name = "burger"
        ))
        val menu = menuRepository.save(
            Menu(
                name = "hamburger",
                price = 5000,
                imageUrl = "https://via.placeholder.com/200x200",
                additionalPrice = 500,
                category = category,
                stock = 50,
                hidden = false
            )
        )

        //when
        menu.softDelete()
        val softDeletedMenu = menuRepository.save(menu)

        //then
        assertThat(softDeletedMenu.deletedAt).isNotNull
    }

    @Test
    fun forceDelete() {
        //given
        val category = categoryRepository.save(Category(
            name = "burger"
        ))
        val menu = menuRepository.save(
            Menu(
                name = "hamburger",
                price = 5000,
                imageUrl = "https://via.placeholder.com/200x200",
                additionalPrice = 500,
                category = category,
                stock = 50,
                hidden = false
            )
        )
        val id = menu.id!!

        //when
        menuRepository.delete(menu)

        //then
        val softDeletedMenu = menuRepository.findById(id)
        assertThat(softDeletedMenu.isEmpty).isTrue
    }
}
