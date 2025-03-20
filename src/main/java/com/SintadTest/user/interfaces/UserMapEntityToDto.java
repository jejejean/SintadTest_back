package com.SintadTest.user.interfaces;


import com.SintadTest.user.models.entity.User;
import com.SintadTest.user.models.response.UserResponse;

public interface UserMapEntityToDto {
    UserResponse entityToDto(User user);
}