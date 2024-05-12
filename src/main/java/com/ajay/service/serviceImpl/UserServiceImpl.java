package com.ajay.service.serviceImpl;

import com.ajay.config.JwtProvider;
import com.ajay.model.User;
import com.ajay.repository.UserRepository;
import com.ajay.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtProvider jwtProvider;


    /**
     * @param jwt
     * @return
     * @throws Exception
     */
    @Override
    public User findUserByJwtToken(String jwt) throws Exception {
        String email = jwtProvider.getEmailFromJwtToken(jwt);
        User user = finduserByEmail(email);
        return user;
    }

    /**
     * @param email
     * @return
     * @throws Exception
     */
    @Override
    public User finduserByEmail(String email) throws Exception {
        User user = Optional.ofNullable(userRepository.findByEmail(email)).orElseThrow(()-> new Exception("User Not found"));
        return user;
    }
}
