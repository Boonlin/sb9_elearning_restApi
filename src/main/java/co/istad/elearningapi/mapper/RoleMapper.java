package co.istad.elearningapi.mapper;

import co.istad.elearningapi.dto.RoleDto;
import co.istad.elearningapi.model.Authority;
import co.istad.elearningapi.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "authorities", qualifiedByName = "toAuthorities")
    RoleDto toRoleDto(Role role);

    List<RoleDto> toRoleListDto(List<Role> roles);

    @Named("toAuthorities")
    default List<String> toAuthorities(List<Authority> authorities) {
        return authorities
                .stream()
                .map(Authority::getName)
                .toList();
    }
}
