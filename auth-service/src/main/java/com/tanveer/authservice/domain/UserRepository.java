package com.tanveer.authservice.domain;

import com.tanveer.authservice.infrastructure.exception.CustomException;

public interface UserRepository {
    User save(User user);
    User update(User user,String id);
    User findByEmail(String email) throws CustomException;
}
