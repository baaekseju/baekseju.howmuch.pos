package com.baekseju.howmuch.pos.service

import com.baekseju.howmuch.pos.entity.SetMenu
import com.baekseju.howmuch.pos.repository.SetMenuRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ActiveProfiles
import java.time.Instant

@SpringBootTest
@ActiveProfiles("dev")
internal class SetMenuServiceTest {
    @Autowired
    private lateinit var setMenuService: SetMenuService

    @MockBean
    private lateinit var setMenuRepository: SetMenuRepository

    private val setMenus = ArrayList<SetMenu>()

    private fun <T> any(): T {
        return Mockito.any()
    }

    @BeforeEach
    fun setup() {
        setMenus()
    }

    private fun setMenus() {
        setMenus.add(
            SetMenu(
                id = 1,
                name = "hamburger set",
                price = 9900,
                imageUrl = "https://via.placeholder.com/200x200",
                hidden = false,
                createdAt = Instant.now(),
                updatedAt = Instant.now()
            )
        )
        setMenus.add(
            SetMenu(
                id = 2,
                name = "chicken set",
                price = 12900,
                imageUrl = "https://via.placeholder.com/200x200",
                hidden = false,
                createdAt = Instant.now(),
                updatedAt = Instant.now()
            )
        )
        setMenus.add(
            SetMenu(
                id = 3,
                name = "double hamburger set",
                price = 19900,
                imageUrl = "https://via.placeholder.com/200x200",
                hidden = true,
                createdAt = Instant.now(),
                updatedAt = Instant.now()
            )
        )
    }

    @Test
    fun getMenusHiddenIsFalse() {
        given(setMenuRepository.findAllByHiddenAndDeletedAtIsNull(false)).willReturn(
            setMenus.filter { !it.hidden }
        )

        val setMenuDtos = setMenuService.getSetMenus(false)

        assertThat(setMenuDtos.size).isEqualTo(2)
    }

    @Test
    fun getMenusHiddenIsTrue() {
        given(setMenuRepository.findAllByHiddenAndDeletedAtIsNull(true)).willReturn(
            setMenus.filter { it.hidden }
        )

        val setMenuDtos = setMenuService.getSetMenus(true)

        assertThat(setMenuDtos.size).isEqualTo(1)
    }
}
