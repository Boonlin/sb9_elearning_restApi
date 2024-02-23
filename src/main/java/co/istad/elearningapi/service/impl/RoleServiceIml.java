package co.istad.elearningapi.service.impl;

import co.istad.elearningapi.dto.RoleDto;
import co.istad.elearningapi.mapper.RoleMapper;
import co.istad.elearningapi.model.Role;
import co.istad.elearningapi.repository.RoleRepository;
import co.istad.elearningapi.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@Service
@RequiredArgsConstructor
public class RoleServiceIml implements RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    public List<RoleDto> findList() {
        List<Role> roles = roleRepository.findAll();
        return roleMapper.toRoleListDto(roles);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public RoleDto findByName(String name) {
        Role role = roleRepository.findByName(name).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Role has not been found!"));
        ;
        return roleMapper.toRoleDto(role);
    }
}
