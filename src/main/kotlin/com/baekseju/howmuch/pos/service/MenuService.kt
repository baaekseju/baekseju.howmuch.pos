package com.baekseju.howmuch.pos.service

import com.baekseju.howmuch.pos.dto.MenuDto
import com.baekseju.howmuch.pos.mapper.MenuMapper
import com.baekseju.howmuch.pos.repository.MenuRepository
import org.springframework.stereotype.Service

@Service
class MenuService(val menuRepository: MenuRepository, val menuMapper: MenuMapper) {

    fun getMenuList(): List<MenuDto> {
        val menuList = menuRepository.findAllByHiddenIsFalseAndDeletedAtIsNull()
        return menuMapper.menuEntitiesToDtos(menuList)
    }

    fun getMenuDetail(menuId: Int): MenuDto {
        val menuEntity = menuRepository.findByIdAndHiddenIsFalseAndDeletedAtIsNull(menuId).get()
        return menuMapper.menuEntityToDto(menuEntity)
    }

    fun addMenu(menuDto: MenuDto): MenuDto {
        val menuEntity = menuRepository.save(menuMapper.menuDtoToEntity(menuDto))
        return menuMapper.menuEntityToDto(menuEntity)
    }

    fun updateMenu(menuId: Int, menuDto: MenuDto): MenuDto {
        val menuEntity = menuRepository.findById(menuId).get()
        menuEntity.updateMenu(menuDto)
        menuRepository.save(menuEntity)
        return menuMapper.menuEntityToDto(menuEntity)
    }

    fun deleteMenu(menuId: Int, force: Boolean): String {
        val menuEntity = menuRepository.findById(menuId).get()
        return if (force) {
            menuRepository.delete(menuEntity)
            "force delete success"
        } else {
            menuEntity.softDelete()
            menuRepository.save(menuEntity)
            "soft delete success"
        }
    }
}
