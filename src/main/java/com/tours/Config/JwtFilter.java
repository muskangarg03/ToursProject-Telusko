package com.tours.Config;

import com.tours.Service.CustomUserDetailsService;
import com.tours.Service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private ApplicationContext context;

    // Example: Token blacklist (in-memory, replace with a persistent store for production)
    private static final Set<String> blacklistedTokens = Collections.synchronizedSet(new HashSet<>());

    public static void addToBlacklist(String token) {
        blacklistedTokens.add(token);
    }

    private boolean isTokenBlacklisted(String token) {
        return blacklistedTokens.contains(token);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        String token = null;
        String userName = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);  // Extract the token
            if (isTokenBlacklisted(token)) {  // Check if the token is blacklisted
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Token has been invalidated. Please log in again.");
                return; // Stop further processing
            }
            userName = jwtService.extractUserName(token);  // Extract username from the token
        }

        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = context.getBean(CustomUserDetailsService.class).loadUserByUsername(userName);

            if (jwtService.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));  // Attach request details
                SecurityContextHolder.getContext().setAuthentication(authToken);  // Set authentication context
            }
        }

        filterChain.doFilter(request, response);  // Continue the filter chain
    }
}


//@Component
//public class JwtFilter extends OncePerRequestFilter {
//
//    @Autowired
//    JwtService jwtService;
//
//    @Autowired
//    ApplicationContext context;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        String authHeader = request.getHeader("Authorization");
//        String token = null;
//        String userName = null;
//
//        if (authHeader != null && authHeader.startsWith("Bearer ")) {
//            token = authHeader.substring(7);  // Extract token from the header
//            userName = jwtService.extractUserName(token);  // Extract username from the token
//        }
//
//        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//            UserDetails userDetails = context.getBean(CustomUserDetailsService.class).loadUserByUsername(userName);
//
//            if (jwtService.validateToken(token, userDetails)) {
//                UsernamePasswordAuthenticationToken authToken =
//                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));  // Attach request details to the authentication
//                SecurityContextHolder.getContext().setAuthentication(authToken);  // Set authentication in the context
//            }
//        }
//
//        filterChain.doFilter(request, response);  // Continue the filter chain
//    }
//}