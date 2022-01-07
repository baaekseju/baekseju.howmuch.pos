package com.baekseju.howmuch.pos.controller

import com.baekseju.howmuch.pos.dto.SetMenuDto
import com.baekseju.howmuch.pos.service.SetMenuService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/setmenus")
class SetMenuController(val setMenuService: SetMenuService) {

    @GetMapping
    fun getSetMenus(@RequestParam(defaultValue = "false") hidden: Boolean): List<SetMenuDto> {
        return setMenuService.getSetMenus(hidden)
    }

    @GetMapping("/{setMenuId}")
    fun getSetMenu(@PathVariable setMenuId: Int): SetMenuDto {
        return setMenuService.getSetMenu(setMenuId)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addMenu(@RequestBody setMenu: SetMenuDto): SetMenuDto {
        return setMenuService.addMenu(setMenu)
    }

    @PutMapping("/{setMenuId}")
    fun putSetMenu(@PathVariable setMenuId: Int, @RequestBody setMenu: SetMenuDto): SetMenuDto {
        return setMenuService.updateSetMenu(setMenuId, setMenu)
    }

    @DeleteMapping("/{setMenuId}")
    fun deleteSetMenu(@PathVariable setMenuId: Int, @RequestParam(defaultValue = "false") force: Boolean): String {
        return setMenuService.deleteSetMenu(setMenuId, force)
    }
}
