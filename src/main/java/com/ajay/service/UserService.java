package com.ajay.service;

import com.ajay.model.User;

public interface UserService {

    User findUserByJwtToken(String jwt) throws Exception;
    User finduserByEmail(String email) throws Exception;
}
