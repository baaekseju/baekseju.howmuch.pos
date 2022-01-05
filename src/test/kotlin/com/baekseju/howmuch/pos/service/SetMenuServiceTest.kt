package com.baekseju.howmuch.pos.service

import com.baekseju.howmuch.pos.dto.SetMenuDto
import com.baekseju.howmuch.pos.entity.SetMenu
import com.baekseju.howmuch.pos.repository.SetMenuRepository
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ActiveProfiles
import java.time.Instant
import java.util.*
import javax.persistence.EntityNotFoundException

@SpringBootTest
@ActiveProfiles("dev")
internal class SetMenuServiceTest {
    @Autowired
    private lateinit var setMenuService: SetMenuService

    @MockBean
    private lateinit var setMenuRepository: SetMenuRepository

    private val setMenus = mutableListOf<SetMenu>()

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
    fun getSetMenusHiddenIsFalse() {
        given(setMenuRepository.findAllByHiddenAndDeletedAtIsNull(false)).willReturn(
            setMenus.filter { !it.hidden }
        )

        val setMenuDtos = setMenuService.getSetMenus(false)

        then(setMenuRepository).should().findAllByHiddenAndDeletedAtIsNull(false)
        assertThat(setMenuDtos.size).isEqualTo(2)
    }

    @Test
    fun getSetMenusHiddenIsTrue() {
        given(setMenuRepository.findAllByHiddenAndDeletedAtIsNull(true)).willReturn(
            setMenus.filter { it.hidden }
        )

        val setMenuDtos = setMenuService.getSetMenus(true)

        then(setMenuRepository).should().findAllByHiddenAndDeletedAtIsNull(true)
        assertThat(setMenuDtos.size).isEqualTo(1)
    }

    @Test
    fun getExistSetMenu() {
        val id = 1
        given(setMenuRepository.findById(id)).willReturn(Optional.of(setMenus.first { it.id == id }))

        val setMenu = setMenuService.getSetMenu(id)

        then(setMenuRepository).should().findById(id)
        assertThat(setMenu.id).isEqualTo(id)
    }

    @Test
    fun getNotExistSetMenu() {
        val id = 999
        given(setMenuRepository.findById(id)).willReturn(Optional.empty())

        assertThatThrownBy { setMenuService.getSetMenu(id) }
            .isInstanceOf(EntityNotFoundException::class.java)
        then(setMenuRepository).should().findById(id)
    }

    @Test
    fun addMenu() {
        val id = 1
        val setMenuDtoMock = SetMenuDto(
            name = "hamburger set",
            price = 9900,
            imageUrl = "https://via.placeholder.com/200x200",
            hidden = false,
        )
        given(setMenuRepository.save(any())).will {
            val setMenu: SetMenu = it.getArgument(0)
            SetMenu(
                id = id,
                name = setMenu.name,
                price = setMenu.price,
                imageUrl = setMenu.imageUrl,
                hidden = setMenu.hidden,
                createdAt = Instant.now(),
                updatedAt = Instant.now()
            )
        }

        val setMenuDto = setMenuService.addMenu(setMenuDtoMock)

        then(setMenuRepository).should().save(any())
        assertThat(setMenuDto.id).isEqualTo(id)
    }
}
