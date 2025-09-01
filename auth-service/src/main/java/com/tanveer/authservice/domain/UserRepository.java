package com.tanveer.authservice.domain;

import com.tanveer.authservice.infrastructure.exception.CustomException;

public interface UserRepository {
    User save(User user) throws CustomException;
    User update(User user,String id) throws CustomException;
    User findByEmail(String email) throws CustomException;
}
