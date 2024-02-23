package co.istad.elearningapi.mapper;

import co.istad.elearningapi.dto.RegisterDto;
import co.istad.elearningapi.dto.UserCreationDto;
import co.istad.elearningapi.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
@Mapper(componentModel = "spring")
public interface UserMapper {
    // User is target
    // UserCreationDto is sources
    User fromUserCreationDto(UserCreationDto userCreationDto);
    UserCreationDto mapRegisterDtoToUserCreationDto(RegisterDto registerDto);
}
