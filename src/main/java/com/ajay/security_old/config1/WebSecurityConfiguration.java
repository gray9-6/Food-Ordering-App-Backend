//package com.ajay.security.config1;
//
//
//
//import com.ajay.security.filters.JwtRequestFilter;
//import com.ajay.security.jwt.UserDetailsServiceImpl;
//import jakarta.servlet.http.HttpServletRequest;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//
//import java.util.Arrays;
//import java.util.Collections;
//
//
///**
// * WebSecurityConfiguration class is responsible for configuring Spring Security for the application.
// * It defines security rules and filters to control access to endpoints and enable JWT authentication.
// *
// * This configuration class is annotated with @Configuration and @EnableWebSecurity to indicate
// * that it provides configuration for Spring Security and enables Spring Security's web security features.
// *
// * It defines a SecurityFilterChain bean named securityFilterChain that specifies the security configuration
// * for the application. The securityFilterChain method configures HTTP security, including CSRF protection,
// * authorization rules, session management, and filters.
// *
// * The JwtRequestFilter and UserDetailsServiceImpl beans are injected into the configuration class and
// * used to handle JWT authentication and retrieve user details from the database, respectively.
// *
// * Additionally, this class provides beans for configuring the password encoder and authentication manager.
// * The passwordEncoder bean specifies the BCryptPasswordEncoder for encoding passwords securely,
// * and the authenticationManager bean retrieves the AuthenticationManager from the AuthenticationConfiguration.
// */
//@Configuration
//@EnableWebSecurity
//@RequiredArgsConstructor
//@Slf4j
//public class WebSecurityConfiguration {
//
//    private final JwtRequestFilter jwtRequestFilter;
//    private final UserDetailsServiceImpl userDetailsServiceImpl;
//
//    /**
//     * Configures the security filter chain for the application.
//     *
//     * @param httpSecurity HttpSecurity object for configuring HTTP security
//     * @return SecurityFilterChain object representing the security filter chain configuration
//     * @throws Exception if an error occurs during security configuration
//     */
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        log.info("Inside securityFilterChain method");
//
//        return httpSecurity
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(
//                        req -> req.
//                                requestMatchers("/api/admin/**").hasAnyRole("RESTAURANT_OWNER","ADMIN")
//                                .requestMatchers("/api/**").authenticated()
//                                .anyRequest().permitAll()
//                )
//                .userDetailsService(userDetailsServiceImpl)
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .addFilterBefore(jwtRequestFilter,UsernamePasswordAuthenticationFilter.class)
//                .build();
//    }
//
//    /**
//     * Configures the password encoder for encoding passwords securely.
//     *
//     * @return PasswordEncoder object representing the BCryptPasswordEncoder
//     */
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    /**
//     * Retrieves the AuthenticationManager from the AuthenticationConfiguration.
//     *
//     * @param authenticationConfiguration AuthenticationConfiguration object for retrieving the AuthenticationManager
//     * @return AuthenticationManager object representing the authentication manager
//     * @throws Exception if an error occurs while retrieving the authentication manager
//     */
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }
//
//}
//
//
//
//
//
//
//
//
//
//
//
//
//
////@Configuration
////@EnableWebSecurity
////@RequiredArgsConstructor
////@Slf4j
////public class WebSecurityConfiguration {
////
////    private final JwtRequestFilter jwtRequestFilter;
////    private final UserDetailsServiceImpl userDetailsServiceImpl;
////
////    @Bean
////    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
////        log.info("Inside securityFilterChain method");
////
////        return httpSecurity
////                .csrf(AbstractHttpConfigurer::disable)
////                .authorizeHttpRequests(
////                        req -> req.requestMatchers("/authenticate","/sign-up","/order/**")
////                                .permitAll()
////                                .requestMatchers("/api/**")
////                                .authenticated()
////                                .anyRequest()
////                                .authenticated()
////                )
////                .userDetailsService(userDetailsServiceImpl)  // we need to tell which user details service spring needs to use
////                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
////                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
////                .build();
////
//////        return httpSecurity.
//////                csrf()
//////                .disable()
//////                .authorizeHttpRequests()
//////                .requestMatchers("/authenticate","/sign-up","/order/**","/api/**")
//////                .permitAll()
//////                .and()
//////                .authorizeHttpRequests()
//////                .requestMatchers("/apis/**")
//////                .authenticated()
//////                .and()
//////                .sessionManagement()
//////                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//////                .and()
//////                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
//////                .build();
////    }
////
////    @Bean
////    public PasswordEncoder passwordEncoder(){
////        return new BCryptPasswordEncoder();
////    }
////
////    @Bean
////    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
////        return authenticationConfiguration.getAuthenticationManager();
////    }
////
////
////}
