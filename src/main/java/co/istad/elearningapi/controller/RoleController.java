package co.istad.elearningapi.controller;

import co.istad.elearningapi.dto.RoleDto;
import co.istad.elearningapi.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {
    private  final RoleService roleService;
    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping
    List<RoleDto> findList(){
        return roleService.findList();
    }
    @GetMapping("/{name}")
    RoleDto findByName(@PathVariable String name){
        return roleService.findByName(name);
    }
}
