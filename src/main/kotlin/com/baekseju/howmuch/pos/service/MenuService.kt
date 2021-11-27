package com.baekseju.howmuch.pos.service

import com.baekseju.howmuch.pos.dto.MenuDto
import com.baekseju.howmuch.pos.repository.MenuRepository
import org.springframework.stereotype.Service

@Service
class MenuService(val menuRepository: MenuRepository) {
    fun getMenuList(): List<MenuDto> {
        val menuList = menuRepository.findAll()
        return menuList.map { menu -> menu.toDto() }
    }

    fun addMenu(menuDto: MenuDto): MenuDto {
        val menuEntity = menuRepository.save(menuDto.toEntity())
        return menuEntity.toDto()
    }
}