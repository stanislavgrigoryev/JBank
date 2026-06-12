package com.jbank.authservice.mapper;

import com.jbank.authservice.controller.response.UserResponse;
import com.jbank.authservice.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    @Mapping(target = "user.passport", ignore = true)
    UserResponse toUserResponse(User user);
}
