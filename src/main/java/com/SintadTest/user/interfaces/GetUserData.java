package com.SintadTest.user.interfaces;


import com.SintadTest.user.models.entity.User;

public interface GetUserData {
    User getUserByUsername(String username);
}