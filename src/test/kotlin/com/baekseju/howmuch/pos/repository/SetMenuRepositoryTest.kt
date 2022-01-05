package com.baekseju.howmuch.pos.repository

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
}
