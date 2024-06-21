package co.istad.elearningapi.service;

import co.istad.elearningapi.dto.UserCreationDto;
import co.istad.elearningapi.model.User;

import java.util.List;

public interface  UserService {
void createNew(UserCreationDto userCreationDto);

    List<UserCreationDto> findAllUserListByOrder();
}
