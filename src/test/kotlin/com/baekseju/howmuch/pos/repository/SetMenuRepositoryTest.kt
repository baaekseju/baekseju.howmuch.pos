package com.baekseju.howmuch.pos.repository

import com.baekseju.howmuch.pos.dto.SetMenuDto
import com.baekseju.howmuch.pos.entity.SetMenu
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("dev")
internal class SetMenuRepositoryTest {
    @Autowired
    private lateinit var setMenuRepository: SetMenuRepository

    @BeforeEach
    fun setDB() {
        setMenuRepository.save(
            SetMenu(
                name = "hamburger set",
                price = 9900,
                imageUrl = "https://via.placeholder.com/200x200",
                hidden = false
            )
        )
        setMenuRepository.save(
            SetMenu(
                name = "chicken set",
                price = 12900,
                imageUrl = "https://via.placeholder.com/200x200",
                hidden = false
            )
        )
        setMenuRepository.save(
            SetMenu(
                name = "double hamburger set",
                price = 19900,
                imageUrl = "https://via.placeholder.com/200x200",
                hidden = true
            )
        )
    }

    @AfterEach
    fun resetDB() {
        setMenuRepository.deleteAll()
    }

    @Test
    fun findAllByHiddenIsFalseAndDeleteAtIsNull() {
        //when
        val setMenus = setMenuRepository.findAllByHiddenAndDeletedAtIsNull(false)

        //then
        assertThat(setMenus.size).isEqualTo(2)
    }

    @Test
    fun findAllByHiddenIsTrueAndDeleteAtIsNull() {
        //when
        val setMenus = setMenuRepository.findAllByHiddenAndDeletedAtIsNull(true)

        //then
        assertThat(setMenus.size).isEqualTo(1)
    }

    @Test
    fun save() {
        val setMenu = SetMenu(
            name = "hamburger set",
            price = 9900,
            imageUrl = "https://via.placeholder.com/200x200",
            hidden = false
        )

        val savedSetMenu = setMenuRepository.save(setMenu)

        assertThat(savedSetMenu.id).isNotNull
        assertThat(savedSetMenu.createdAt).isNotNull
        assertThat(savedSetMenu.updatedAt).isNotNull
    }

    @Test
    fun update() {
        val setMenu = setMenuRepository.save(
            SetMenu(
                name = "hamburger set",
                price = 9900,
                imageUrl = "https://via.placeholder.com/200x200",
                hidden = false
            )
        )
        setMenu.updateSetMenu(
            SetMenuDto(
                name = "burger set",
                price = 11900,
                imageUrl = "https://via.placeholder.com/300x300",
                hidden = false
            )
        )

        val updatedSetMenu = setMenuRepository.save(setMenu)

        assertThat(setMenu.name).isEqualTo(updatedSetMenu.name)
        assertThat(setMenu.price).isEqualTo(updatedSetMenu.price)
        assertThat(setMenu.imageUrl).isEqualTo(updatedSetMenu.imageUrl)
        assertThat(setMenu.hidden).isEqualTo(updatedSetMenu.hidden)
        assertThat(setMenu.createdAt).isEqualTo(updatedSetMenu.createdAt)
        assertThat(setMenu.updatedAt).isNotEqualTo(updatedSetMenu.updatedAt)
    }
}
