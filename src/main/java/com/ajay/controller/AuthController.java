package com.ajay.controller;

import com.ajay.config.JwtProvider;
import com.ajay.enums.UserRole;
import com.ajay.model.Cart;
import com.ajay.model.User;
import com.ajay.repository.CartRepository;
import com.ajay.repository.UserRepository;
import com.ajay.request.LoginRequest;
import com.ajay.response.AuthResponse;
import com.ajay.service.CustomerUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private CustomerUserDetailsService customerUserDetailsService;
    @Autowired
    private CartRepository cartRepository;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws Exception {

        User isEmailExist = userRepository.findByEmail(user.getEmail());
        if(isEmailExist != null){  // if the email already exists
            throw new Exception("Email is already used with another account");
        }

        // if not , then create the user
        User createdUser = User.builder()
                .email(user.getEmail())
                .role(user.getRole())
                .fullName(user.getFullName())
                .password(passwordEncoder.encode(user.getPassword()))
                .build();
        // save the user
        User savedUser = userRepository.save(createdUser);

        // after save the user , create the cart for that user
        Cart cart = Cart.builder().customer(savedUser).build();
        cartRepository.save(cart);

        // create the authentication
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // generate the token
        String jwt = jwtProvider.generateToken(authentication);

        // prepare the response
        AuthResponse authResponse = AuthResponse.builder()
                .jwt(jwt)
                .message("Registered Success")
                .role(savedUser.getRole())
                .build();

        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signIn(@RequestBody LoginRequest loginRequest){
        String userName = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        // get the authentication
        Authentication authentication = authenticate(userName,password);

        // generate the token
        String jwt = jwtProvider.generateToken(authentication);

        // prepare the response
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String role = authorities.isEmpty() ? null : authorities.iterator().next().getAuthority();
        AuthResponse authResponse = AuthResponse.builder()
                .jwt(jwt)
                .message("Login Success")
//                .role(UserRole.valueOf(role))
                .role(UserRole.valueOf(role))
                .build();

        return new ResponseEntity<>(authResponse, HttpStatus.OK);

    }

    private Authentication authenticate(String userName, String password) {
        // get the user and check if it exists or not
        UserDetails userDetails = customerUserDetailsService.loadUserByUsername(userName);
        if(userDetails == null){
            throw new BadCredentialsException("Invalid username... !!!!");
        }

        // check if the password matches or not
        if(!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new BadCredentialsException("Invalid password... !!!");
        }

        // if all matches then return the authentication
        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());

    }
}
