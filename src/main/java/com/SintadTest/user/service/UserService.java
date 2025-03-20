package com.SintadTest.user.service;

import com.SintadTest.exceptions.NotFoundException;
import com.SintadTest.user.interfaces.GetUserData;
import com.SintadTest.user.models.entity.User;
import com.SintadTest.user.repository.UserRepository;
import com.SintadTest.utils.Messages;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements GetUserData {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUserByUsername(String username) {
        Optional<User> optionalUser = userRepository.findUserEntityByEmail(username);
        if(optionalUser.isEmpty()) {
            throw new NotFoundException(Messages.USER_NOT_FOUND.getMessage());
        }
        return optionalUser.get();
    }
}
