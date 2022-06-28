package com.baekseju.howmuch.pos.service

import com.baekseju.howmuch.pos.dto.MenuDto
import com.baekseju.howmuch.pos.entity.Category
import com.baekseju.howmuch.pos.entity.Menu
import com.baekseju.howmuch.pos.repository.CategoryRepository
import com.baekseju.howmuch.pos.repository.MenuRepository
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito
import org.mockito.BDDMockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import java.time.Instant
import java.util.*
import javax.persistence.EntityNotFoundException

@SpringBootTest
internal class MenuServiceTest {

    @Autowired
    private lateinit var menuService: MenuService

    @MockBean
    private lateinit var menuRepository: MenuRepository

    @MockBean
    private lateinit var categoryRepository: CategoryRepository

    private val menus = mutableListOf<Menu>()
    private val categories = mutableListOf<Category>()

    private fun <T> any(): T {
        return BDDMockito.any()
    }

    @BeforeEach
    fun setup() {
        setMenus()
        setCategories()
    }

    private fun setMenus() {
        menus.add(
            Menu(
                id = 1,
                name = "hamburger",
                price = 5000,
                imageUrl = "https://via.placeholder.com/200x200",
                additionalPrice = 500,
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
                name = "cola",
                price = 1500,
                imageUrl = "https://via.placeholder.com/200x200",
                additionalPrice = 0,
                category = Category(2, "음료", Instant.now(), Instant.now()),
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
                name = "wing",
                price = 6000,
                imageUrl = "https://via.placeholder.com/200x200",
                additionalPrice = 2000,
                category = Category(3, "사이드", Instant.now(), Instant.now()),
                stock = 10,
                hidden = true,
                createdAt = Instant.now(),
                updatedAt = Instant.now()
            )
        )
    }

    private fun setCategories() {
        categories.add(
            Category(
                id = 100,
                name = "burger"
            )
        )
    }

    @Test
    fun getMenusHiddenIsFalse() {
        //given
        given(menuRepository.findAllByHiddenAndDeletedAtIsNull(false)).willReturn(
            menus.filter { menu -> menu.hidden == false && menu.deletedAt == null }
        )

        //when
        val menuDtos = menuService.getMenus(false)

        //then
        then(menuRepository).should().findAllByHiddenAndDeletedAtIsNull(false)
        assertThat(menuDtos.size).isEqualTo(1)
        assertThat(menuDtos[0].hidden).isFalse
    }

    @Test
    fun getMenusHiddenIsTrue() {
        //given
        given(menuRepository.findAllByHiddenAndDeletedAtIsNull(true)).willReturn(
            menus.filter { menu -> menu.hidden == true && menu.deletedAt == null }
        )

        //when
        val menuDtos = menuService.getMenus(true)

        //then
        then(menuRepository).should().findAllByHiddenAndDeletedAtIsNull(true)
        assertThat(menuDtos.size).isEqualTo(1)
        assertThat(menuDtos[0].hidden).isTrue
    }

    @Test
    fun getMenu() {
        //given
        val id = 1
        given(menuRepository.findById(id)).willReturn(Optional.of(menus.first { menu -> menu.id == id }))

        //when
        val menuDetail = menuService.getMenu(id)

        //then
        then(menuRepository).should().findById(id)
        assertThat(menuDetail.id).isEqualTo(id)
    }

    @Test
    fun getNotExistMenu() {
        //given
        val id = 999
        given(menuRepository.findById(id)).willReturn(Optional.empty())

        //when, then
        assertThatThrownBy { menuService.getMenu(id) }
            .isInstanceOf(EntityNotFoundException::class.java)
        then(menuRepository).should().findById(id)
    }

    @Test
    fun addMenu() {
        //given
        val menuDtoMock = MenuDto(
            name = "hamburger",
            price = 5000,
            imageUrl = "https://via.placeholder.com/200x200",
            additionalPrice = 500,
            categoryId = 100,
            stock = 50,
            hidden = false
        )
        given(categoryRepository.findById(menuDtoMock.categoryId!!)).willReturn(
            Optional.of(
                categories.first { category -> category.id == menuDtoMock.categoryId }
            )
        )
        val id = 1
        given(menuRepository.save(any())).will {
            val menu: Menu = it.getArgument(0)
            Menu(
                id = id,
                name = menu.name,
                price = menu.price,
                imageUrl = "https://via.placeholder.com/200x200",
                additionalPrice = menu.additionalPrice,
                category = menu.category,
                stock = menu.stock,
                hidden = menu.hidden,
                createdAt = Instant.now(),
                updatedAt = Instant.now()
            )
        }


        //when
        val menuDto = menuService.addMenu(menuDtoMock)

        //then
        then(categoryRepository).should().findById(menuDtoMock.categoryId!!)
        then(menuRepository).should().save(any())
        assertThat(menuDto.id).isEqualTo(id)
    }

    @Test
    fun updateMenu() {
        //given
        val menuDtoMock = MenuDto(
            name = "hamburger",
            price = 10000,
            imageUrl = "https://via.placeholder.com/300x300",
            additionalPrice = 1000,
            categoryId = 100,
            stock = 500,
            hidden = false
        )
        given(categoryRepository.findById(menuDtoMock.categoryId!!)).willReturn(
            Optional.of(
                categories.first { category -> category.id == menuDtoMock.categoryId }
            )
        )
        val id = 1
        given(menuRepository.findById(id)).willReturn(Optional.of(menus.first { it.id == id }))

        //when
        val menuDto = menuService.updateMenu(id, menuDtoMock)

        //then
        then(categoryRepository).should().findById(menuDtoMock.categoryId!!)
        then(menuRepository).should().save(any())
        assertThat(menuDto.price).isEqualTo(menuDtoMock.price)
        assertThat(menuDto.imageUrl).isEqualTo(menuDtoMock.imageUrl)
        assertThat(menuDto.additionalPrice).isEqualTo(menuDtoMock.additionalPrice)
        assertThat(menuDto.categoryId).isEqualTo(menuDtoMock.categoryId)
        assertThat(menuDto.stock).isEqualTo(menuDtoMock.stock)
        assertThat(menuDto.hidden).isEqualTo(menuDtoMock.hidden)
    }

    @Test
    fun updateNotExistMenu() {
        val menuDtoMock = MenuDto(
            name = "hamburger",
            price = 10000,
            imageUrl = "https://via.placeholder.com/200x200",
            additionalPrice = 1000,
            categoryId = 100,
            stock = 500,
            hidden = false
        )
        given(categoryRepository.findById(menuDtoMock.categoryId!!)).willReturn(
            Optional.of(
                categories.first { category -> category.id == menuDtoMock.categoryId }
            )
        )
        val id = 999
        given(menuRepository.findById(id)).willReturn(Optional.empty())

        //when, then
        assertThatThrownBy { menuService.updateMenu(id, menuDtoMock) }
            .isInstanceOf(EntityNotFoundException::class.java)
        then(menuRepository).should(never()).save(any())
    }

    @Test
    fun softDeleteMenu() {
        val id = 1
        given(menuRepository.findById(id)).willReturn(Optional.of(menus.first { it.id == id }))

        val result = menuService.deleteMenu(id, false)

        then(menuRepository).should().save(any())
        assertThat(result).isEqualTo("soft delete success")
    }

    @Test
    fun forceDeleteMenu() {
        val id = 1
        given(menuRepository.findById(id)).willReturn(Optional.of(menus.first { it.id == id }))

        val result = menuService.deleteMenu(id, true)

        then(menuRepository).should().delete(any())
        assertThat(result).isEqualTo("force delete success")
    }

    @Test
    fun deleteNotExistMenu() {
        //given
        val id = 999
        given(menuRepository.findById(id)).willReturn(Optional.empty())

        //when, then
        assertThatThrownBy { menuService.deleteMenu(id, false) }
            .isInstanceOf(EntityNotFoundException::class.java)
        then(menuRepository).should(never()).delete(any())
        then(menuRepository).should(never()).save(any())
    }
}
