package co.istad.elearningapi.controller;


import co.istad.elearningapi.dto.UserCreationDto;
import co.istad.elearningapi.model.User;
import co.istad.elearningapi.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@SecurityRequirement(name="bearerAuth")
public class UserController {
    private final UserService userService;
    @GetMapping()
    List<UserCreationDto> findAllUserListByOrder(){
        return userService.findAllUserListByOrder();
    }
}
