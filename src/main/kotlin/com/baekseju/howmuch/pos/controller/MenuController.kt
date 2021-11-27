package com.baekseju.howmuch.pos.controller

import com.baekseju.howmuch.pos.dto.MenuDto
import com.baekseju.howmuch.pos.service.MenuService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/menus")
class MenuController(
    val menuService: MenuService
) {

    @GetMapping
    fun getMenuList(): List<MenuDto> {
        return menuService.getMenuList()
    }

    @GetMapping("/{menuId}")
    fun getMenuDetail(@PathVariable menuId: Int): MenuDto{
        return menuService.getMenuDetail(menuId)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addMenu(@RequestBody menu: MenuDto): MenuDto {
        return menuService.addMenu(menu)
    }

    @PutMapping("/{menuId}")
    @ResponseStatus(HttpStatus.CREATED)
    fun putMenu(@PathVariable menuId: Int, @RequestBody menu: MenuDto): MenuDto{
        return menuService.updateMenu(menuId, menu)
    }

    @DeleteMapping("/{menuId}")
    fun deleteMenu(@PathVariable menuId: Int): String{
        menuService.softDeleteMenu(menuId)
        return "{}"
    }
}