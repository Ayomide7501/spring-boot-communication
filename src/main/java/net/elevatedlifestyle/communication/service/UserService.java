package net.elevatedlifestyle.communication.service;

import net.elevatedlifestyle.communication.dto.UserDto;
import net.elevatedlifestyle.communication.model.User;

import java.util.List;

public interface UserService {
     void saveUser(UserDto userDto);
     List<UserDto> findAll();
     User findById(Long id);

}
