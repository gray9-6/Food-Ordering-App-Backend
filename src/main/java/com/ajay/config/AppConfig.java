package com.ajay.config;

import com.ajay.service.CustomerUserDetailsService;
import com.ajay.service.serviceImpl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class AppConfig {

    private final JwtTokenValidator jwtTokenValidator;
    private final CustomerUserDetailsService customerUserDetailsService;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        log.info("Inside AppConfig");

        http.sessionManagement(management ->management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(Authorize -> Authorize
                        .requestMatchers("/api/admin/**").hasAnyAuthority("RESTAURANT_OWNER","ADMIN")  // Role based Authentication, only the given role can access these apis
                        .requestMatchers("/api/**").authenticated()   // any user with token can access these apis, regardless or role.
                        .anyRequest().permitAll()  // other than the given above apis,any user can access apis , they don't need token
                )
                .userDetailsService(customerUserDetailsService)
                .addFilterBefore(jwtTokenValidator, UsernamePasswordAuthenticationFilter.class)   // checking if user have provided the token or not
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()));
        return http.build();
    }

    private CorsConfigurationSource corsConfigurationSource() {
        return new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration cfg = new CorsConfiguration();

                // we can set the frontend urls , by which the backend app can be accessible
                cfg.setAllowedOrigins(Arrays.asList(
                        "https://ajay-food.vercel.app/",   // deployed link
                        "https://localhost:3000"           // local host
                ));

                // we can set the method as well (e.g:- PUT,POST,DELETE,GET),by which the backend app can be accessible, when frontend is sending the request
                cfg.setAllowedMethods(Collections.singletonList("*"));  // * means i want to allow all the methods
                cfg.setAllowCredentials(true);
                cfg.setAllowedHeaders(Collections.singletonList("*"));
                cfg.setExposedHeaders(Arrays.asList("Authorization"));
                cfg.setMaxAge(3600L);
                return cfg;
            }
        };
    }

    @Bean
    PasswordEncoder passwordEncoder(){      // whenever new user registered with our app , we will encode the password and then store that encrypted password in our db.
        return new BCryptPasswordEncoder();
    }
}
