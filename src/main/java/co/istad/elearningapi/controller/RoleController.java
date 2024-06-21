package co.istad.elearningapi.controller;

import co.istad.elearningapi.dto.RoleDto;
import co.istad.elearningapi.service.RoleService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
@SecurityRequirement(name="bearerAuth")
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
