//package com.ajay.security.filters;
//
//
//import com.ajay.security.jwt.UserDetailsServiceImpl;
//import com.ajay.security.utils.JwtUtil;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
//
///**
// * JwtRequestFilter is a filter responsible for intercepting incoming HTTP requests
// * and processing JWT authentication. It extracts JWT tokens from the Authorization
// * header, validates them, and sets up Spring Security's authentication context if
// * the token is valid.
// *
// * This filter extends Spring's OncePerRequestFilter, ensuring that it's only executed
// * once per request. It checks for the presence of a JWT token in the Authorization
// * header and delegates the token validation and user authentication to the UserDetailsServiceImpl
// * and JwtUtil classes.
// *
// * If the token is valid and the user is authenticated, the filter sets up the
// * Spring Security authentication context with the authenticated user details.
// *
// * This filter is typically configured in the Spring Security configuration to be
// * applied to certain endpoints or paths that require JWT authentication.
// */
//@Component
//@RequiredArgsConstructor
//@Slf4j
//public class JwtRequestFilter extends OncePerRequestFilter {
//
//    private final UserDetailsServiceImpl userDetailsServiceImpl;
//    private final JwtUtil jwtUtil;
//
//    /**
//     * Overrides the doFilterInternal method of OncePerRequestFilter.
//     * This method intercepts incoming HTTP requests, extracts JWT tokens,
//     * validates them, and sets up Spring Security's authentication context.
//     *
//     * @param request     HttpServletRequest object representing the incoming request
//     * @param response    HttpServletResponse object representing the outgoing response
//     * @param filterChain FilterChain object for invoking the next filter in the chain
//     * @throws ServletException if an error occurs during the filter execution
//     * @throws IOException      if an I/O error occurs during the filter execution
//     */
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//
//        log.info("Inside doFilterInternal method");
//
//        // Extract JWT token from Authorization header
//        String authHeader = request.getHeader("Authorization");
//        String token = null;
//        String username = null;
//
//        if (authHeader != null && authHeader.startsWith("Bearer ")) {
//            token = authHeader.substring(7);
//            username = jwtUtil.extractUserName(token);
//        }
//
//        // If the username is extracted and user authentication is not already set up
//        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//            // Load UserDetails based on the extracted username
//            UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(username);
//
//            // Validate the token against the loaded UserDetails
//            if (jwtUtil.validateToken(token, userDetails)) {
//                // Set up Spring Security authentication context
//                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//
//                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//            }
//        }
//
//        // Continue with the filter chain
//        filterChain.doFilter(request, response);
//    }
//}
//
//
//
////@Component
////@RequiredArgsConstructor
////@Slf4j
////public class JwtRequestFilter extends OncePerRequestFilter {
////
////    private final UserDetailsServiceImpl userDetailsServiceImpl;
////    private final JwtUtil jwtUtil;
////
////
////
////    @Override
////    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
////
////        log.info("Inside doFilterInternal method");
////
////        String authHeader = request.getHeader("Authorization");
////
////        String token = null;
////        String username = null;
////
////        if(authHeader!= null &&  authHeader.startsWith("Bearer ")){
////            token = authHeader.substring(7);
////            username = jwtUtil.extractUserName(token);
////        }
////
////        if(username!= null && SecurityContextHolder.getContext().getAuthentication()==null){
////            UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(username);
////
////            if(jwtUtil.validateToken(token,userDetails)){
////                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
////                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
////
////                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
////
////            }
////        }
////
////        filterChain.doFilter(request,response);
////
////    }
////}
