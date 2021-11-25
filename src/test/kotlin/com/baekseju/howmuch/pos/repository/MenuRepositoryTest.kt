package com.baekseju.howmuch.pos.repository

import com.baekseju.howmuch.pos.entity.Menu
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import java.time.LocalDateTime

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("dev")
internal class MenuRepositoryTest() {
    @Autowired
    private lateinit var menuRepository: MenuRepository

    @BeforeAll
    fun setup(){
        setMenu()
    }

    private fun setMenu(){
        menuRepository.save(Menu(
            name = "hamburger",
            price = 5000,
            additional_price = 500,
            category_id = 100,
            stock = 50,
            hidden = false
            )
        )
        menuRepository.save(Menu(
            name = "cola",
            price = 1500,
            additional_price = 0,
            category_id = 103,
            stock = 999,
            hidden = false,
            deletedAt = null
            )
        )
    }

    @Test
    fun findAll(){
        //when
        val menuList = menuRepository.findAll()

        //then
        assertThat(menuList.size).isEqualTo(2)
        assertThat(menuList[0].name).isEqualTo("hamburger")
        assertThat(menuList[1].name).isEqualTo("cola")
    }
}