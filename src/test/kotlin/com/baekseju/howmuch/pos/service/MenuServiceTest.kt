package com.baekseju.howmuch.pos.service

import com.baekseju.howmuch.pos.entity.Menu
import com.baekseju.howmuch.pos.repository.MenuRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import java.time.LocalDateTime

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class MenuServiceTest() {

    @Autowired
    private lateinit var menuService: MenuService

    @MockBean
    private lateinit var menuRepository: MenuRepository

    val menuList = ArrayList<Menu>()

    @BeforeAll
    fun setup(){
        setMenu()
    }

    private fun setMenu(){
        menuList.add(Menu(
            id = 1,
            name = "hamburger",
            price = 5000,
            additional_price = 500,
            category_id = 100,
            stock = 50,
            hidden = false,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
            deletedAt = null
        ))
        menuList.add(Menu(
            id = 2,
            name = "cola",
            price = 1500,
            additional_price = 0,
            category_id = 103,
            stock = 999,
            hidden = false,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
            deletedAt = null
        ))
    }

    @Test
    fun getMenus(){
        //given
        given(menuRepository.findAll()).willReturn(menuList)

        //when
        val menuDtoList = menuService.getMenuList()

        //then
        then(menuRepository).should().findAll()
        assertThat(menuDtoList.size).isEqualTo(2)
        assertThat(menuDtoList[0].name).isEqualTo("hamburger")
    }
}