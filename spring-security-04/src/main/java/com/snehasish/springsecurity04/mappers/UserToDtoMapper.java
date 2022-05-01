package com.snehasish.springsecurity04.mappers;

import com.snehasish.springsecurity04.models.User;
import com.snehasish.springsecurity04.models.dto.UserDto;
import org.mapstruct.Mapper;

@Mapper
public interface UserToDtoMapper {

    UserDto entityToDto(User user);

}
