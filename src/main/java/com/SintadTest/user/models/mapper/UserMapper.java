package com.SintadTest.user.models.mapper;

import com.SintadTest.user.interfaces.UserMapEntityToDto;
import com.SintadTest.user.models.entity.User;
import com.SintadTest.user.models.response.UserResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements UserMapEntityToDto {
    private final ModelMapper modelMapper;

    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public UserResponse entityToDto(User user) {
        return modelMapper.map(user, UserResponse.class);
    }
}
