package com.baekseju.howmuch.pos.service

import com.baekseju.howmuch.pos.dto.MenuDto
import com.baekseju.howmuch.pos.repository.MenuRepository
import org.springframework.stereotype.Service

@Service
class MenuService(val menuRepository: MenuRepository) {
    fun getMenuList(): List<MenuDto> {
        val menuList = menuRepository.findAll().filter { menu -> menu.deleteAt == null && !menu.hidden }
        return menuList.map { menu -> menu.toDto() }
    }

    fun addMenu(menuDto: MenuDto): MenuDto {
        val menuEntity = menuRepository.save(menuDto.toEntity())
        return menuEntity.toDto()
    }

    fun getMenuDetail(menuId: Int): MenuDto {
        val menuEntity = menuRepository.findById(menuId).get()
        return menuEntity.toDto()
    }

    fun updateMenu(menuId: Int, menuDto: MenuDto): MenuDto {
        val menuEntity = menuRepository.findById(menuId).get()
        menuEntity.updateMenu(menuDto)
        menuRepository.save(menuEntity)
        return menuEntity.toDto()
    }

    fun softDeleteMenu(menuId: Int): MenuDto {
        val menuEntity = menuRepository.findById(menuId).get()
        menuEntity.softDelete()
        menuRepository.save(menuEntity)
        return menuEntity.toDto()
    }
}