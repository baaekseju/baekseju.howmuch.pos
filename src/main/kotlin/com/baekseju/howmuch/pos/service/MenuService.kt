package com.baekseju.howmuch.pos.service

import com.baekseju.howmuch.pos.dto.MenuDto
import com.baekseju.howmuch.pos.mapper.MenuMapper
import com.baekseju.howmuch.pos.repository.CategoryRepository
import com.baekseju.howmuch.pos.repository.MenuRepository
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service
class MenuService(val menuRepository: MenuRepository, val categoryRepository: CategoryRepository, val menuMapper: MenuMapper) {

    fun getMenus(hidden: Boolean): List<MenuDto> {
        val menus = menuRepository.findAllByHiddenAndDeletedAtIsNull(hidden)
        return menuMapper.toDtos(menus)
    }

    fun getMenu(menuId: Int): MenuDto {
        val menu = menuRepository.findById(menuId).orElse(null) ?: throw EntityNotFoundException()
        return menuMapper.toDto(menu)
    }

    fun addMenu(menuDto: MenuDto): MenuDto {
        val category = categoryRepository.findById(menuDto.categoryId!!).orElse(null) ?: throw EntityNotFoundException()
        val menuEntity = menuMapper.toEntity(menuDto)
        menuEntity.category = category
        val menu = menuRepository.save(menuEntity)
        return menuMapper.toDto(menu)
    }

    fun updateMenu(menuId: Int, menuDto: MenuDto): MenuDto {
        val category = categoryRepository.findById(menuDto.categoryId!!).orElse(null) ?: throw EntityNotFoundException()
        val menu = menuRepository.findById(menuId).orElse(null) ?: throw EntityNotFoundException()
        menu.updateMenu(menuDto, category)
        menuRepository.save(menu)
        return menuMapper.toDto(menu)
    }

    fun deleteMenu(menuId: Int, force: Boolean): String {
        val menu = menuRepository.findById(menuId).orElse(null) ?: throw EntityNotFoundException()
        return if (force) {
            menuRepository.delete(menu)
            "force delete success"
        } else {
            menu.softDelete()
            menuRepository.save(menu)
            "soft delete success"
        }
    }
}
