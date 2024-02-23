package co.istad.elearningapi.service;

import co.istad.elearningapi.dto.RoleDto;

import java.util.List;

public interface RoleService {

    List<RoleDto> findList();

    RoleDto findByName(String name);
}
