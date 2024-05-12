//package com.ajay.security.jwt;
//
//import com.ajay.model.User;
//import com.ajay.repository.UserRepository;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.Optional;
//
//
///**
// * UserDetailsServiceImpl is a service class that implements Spring Security's UserDetailsService interface.
// * It is responsible for loading user details (e.g., username, password, authorities) from the database
// * based on the provided username.
// *
// * This service class is annotated with @Service to indicate that it's a Spring-managed service component.
// * It also uses SLF4J logging for logging messages.
// *
// * The UserRepository is autowired into this class to access user data from the database.
// *
// * The loadUserByUsername method is implemented to load user details by username. It queries the database
// * for a user with the specified username and constructs a UserDetails object containing the user's email
// * (as the username), password, and an empty list of authorities.
// *
// * If the user is not found in the database, a UsernameNotFoundException is thrown.
// */
//@Service
//@Slf4j
//public class UserDetailsServiceImpl implements UserDetailsService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    /**
//     * Loads user details by username.
//     *
//     * @param username the username (email) of the user
//     * @return UserDetails object representing the user details
//     * @throws UsernameNotFoundException if the user is not found in the database
//     */
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        log.info("Inside loadUserByUserName method");
//
//        // Find user by email (username) in the database
//        Optional<User> optionalUser = Optional.ofNullable(userRepository.findByEmail(username));
//
//        // Throw exception if user not found
//        if (optionalUser.isEmpty()) {
//            throw new UsernameNotFoundException("User not found: " + username);
//        }
//
//        // Create UserDetails object with user details
//        return new org.springframework.security.core.userdetails.User(
//                optionalUser.get().getEmail(),  // username
//                optionalUser.get().getPassword(),  // password
//                new ArrayList<>()  // empty list of authorities
//        );
//    }
//}
//
//
////@Service
////@Slf4j
////public class UserDetailsServiceImpl implements UserDetailsService {
////
////    @Autowired
////    private UserRepository userRepository;
////
////    @Override
////    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
////        log.info("Inside loadUserByUserName method");
////        Optional<User> optionalUser = userRepository.findFirstByEmail(username);
////
////        if(optionalUser.isEmpty()){
////            throw new UsernameNotFoundException("User not Found",null);
////        }
////
////        return new org.springframework.security.core.userdetails.User(optionalUser.get().getEmail(),optionalUser.get().getPassword(),new ArrayList<>());
////    }
////}
