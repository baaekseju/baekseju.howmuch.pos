package com.baekseju.howmuch.pos.controller

import com.baekseju.howmuch.pos.dto.MenuDto
import com.baekseju.howmuch.pos.dto.MenuJsonView
import com.baekseju.howmuch.pos.service.MenuService
import com.fasterxml.jackson.annotation.JsonView
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/menus")
class MenuController(
    val menuService: MenuService
) {

    @GetMapping
    @JsonView(MenuJsonView.Simple::class)
    fun getMenus(@RequestParam(defaultValue = "false") hidden: Boolean): List<MenuDto> {
        return menuService.getMenus(hidden)
    }

    @GetMapping("/{menuId}")
    @JsonView(MenuJsonView.Detail::class)
    fun getMenuDetail(@PathVariable menuId: Int, @RequestParam(defaultValue = "false") hidden: Boolean): MenuDto{
        return menuService.getMenuDetail(menuId, hidden)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addMenu(@RequestBody menu: MenuDto): MenuDto {
        return menuService.addMenu(menu)
    }

    @PutMapping("/{menuId}")
    fun putMenu(@PathVariable menuId: Int, @RequestBody menu: MenuDto): MenuDto{
        return menuService.updateMenu(menuId, menu)
    }

    @DeleteMapping("/{menuId}")
    fun deleteMenu(@PathVariable menuId: Int, @RequestParam(defaultValue = "false") force: Boolean): String{
        return menuService.deleteMenu(menuId, force)
    }
}
