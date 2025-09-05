package com.tanveer.authservice.domain;

import com.tanveer.authservice.infrastructure.exception.CustomException;

public interface UserService {
    User getUserByEmail(String email) throws CustomException;
    User saveUser(User user) throws CustomException;
}
