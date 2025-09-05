package com.tanveer.authservice.domain;

import com.tanveer.authservice.infrastructure.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getUserByEmail(String email) throws CustomException {
        log.info("Fetching user by {} email address", email);
        return userRepository.findByEmail(email);
    }

    @Override
    public User saveUser(User user){
        log.info("Saving user by {} email address", user.getEmail());
        userRepository.save(user);
        return user;
    }
}

