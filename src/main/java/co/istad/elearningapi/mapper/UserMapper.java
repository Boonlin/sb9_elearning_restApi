package co.istad.elearningapi.mapper;

import co.istad.elearningapi.dto.RegisterDto;
import co.istad.elearningapi.dto.UserCreationDto;
import co.istad.elearningapi.model.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    // User is target
    // UserCreationDto is sources
    User fromUserCreationDto(UserCreationDto userCreationDto);
    UserCreationDto mapRegisterDtoToUserCreationDto(RegisterDto registerDto);

    List<UserCreationDto> toUserListDto(List<User> users);

}
