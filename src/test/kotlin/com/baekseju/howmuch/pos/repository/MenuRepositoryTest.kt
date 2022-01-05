package com.baekseju.howmuch.pos.repository

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

    @AfterEach
    fun initDB() {
        menuRepository.deleteAll()
    }

    @Test
    fun findAll() {
        menuRepository.save(
            Menu(
                name = "hamburger",
                price = 5000,
                imageUrl = "https://via.placeholder.com/200x200",
                additionalPrice = 500,
                categoryId = 100,
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
                categoryId = 103,
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
        val menu = Menu(
            name = "hamburger",
            price = 5000,
            imageUrl = "https://via.placeholder.com/200x200",
            additionalPrice = 500,
            categoryId = 100,
            stock = 50,
            hidden = false
        )

        //when
        val savedMenu = menuRepository.save(menu)

        //then
        assertThat(savedMenu.id).isNotNull
        assertThat(savedMenu.createdAt).isNotNull
        assertThat(savedMenu.updatedAt).isNotNull
    }

    @Test
    fun update() {
        //given
        val menu = Menu(
            name = "hamburger",
            price = 5000,
            imageUrl = "https://via.placeholder.com/200x200",
            additionalPrice = 500,
            categoryId = 100,
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
        val menu = Menu(
            name = "hamburger",
            price = 5000,
            imageUrl = "https://via.placeholder.com/200x200",
            additionalPrice = 500,
            categoryId = 100,
            stock = 50,
            hidden = false
        )
        val savedMenu = menuRepository.save(menu)
        val id = savedMenu.id!!

        //when
        savedMenu.softDelete()
        menuRepository.save(savedMenu)

        //then
        val softDeletedMenu = menuRepository.findById(id)
        assertThat(softDeletedMenu.isPresent).isTrue
        assertThat(softDeletedMenu.get().deletedAt).isNotNull
    }

    @Test
    fun forceDelete() {
        //given
        val menu = Menu(
            name = "hamburger",
            price = 5000,
            imageUrl = "https://via.placeholder.com/200x200",
            additionalPrice = 500,
            categoryId = 100,
            stock = 50,
            hidden = false
        )
        val savedMenu = menuRepository.save(menu)
        val id = savedMenu.id!!

        //when
        menuRepository.delete(savedMenu)

        //then
        val softDeletedMenu = menuRepository.findById(id)
        assertThat(softDeletedMenu.isEmpty).isTrue

    }
}
