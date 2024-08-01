package com.clone.instagram.authSetvice.Config;

import com.clone.instagram.authSetvice.util.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import com.clone.instagram.authSetvice.Service.UserDetailsServiceImpl;
import com.clone.instagram.authSetvice.Service.UserService;

import java.io.IOException;


public class JwtFilter extends OncePerRequestFilter {

    private final JwtConfig jwtConfig;
    private JwtUtils jwtUtils;
    private UserService userService;
    private String serviceUsername;
    private UserDetailsServiceImpl userDetailsService;

    public JwtFilter(JwtConfig jwtConfig, JwtUtils jwtUtils, UserService userService, String serviceUsername) {
        this.jwtConfig = jwtConfig;
        this.jwtUtils = jwtUtils;
        this.userService = userService;
        this.serviceUsername = serviceUsername;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

//        String header = request.getHeader(jwtConfig.getHeader());
//
//        if(header == null || !header.startsWith(jwtConfig.getPrefix())) {
//            chain.doFilter(request, response);
//            return;
//        }
//
//        String token = header.replace(jwtConfig.getPrefix(), "");
//
//
//        if(jwtUtils.validateToken(token)) {
//
//            Claims claims = jwtUtils.getUsernameFromJwtToken(token);
//            String username = claims.getSubject();
//
//            UsernamePasswordAuthenticationToken auth = null;
//
//            if(username.equals(serviceUsername)) {
//
//                List<String> authorities = (List<String>) claims.get("authorities");
//
//                auth = new UsernamePasswordAuthenticationToken(username, null,
//                        authorities
//                                .stream()
//                                .map(SimpleGrantedAuthority::new)
//                                .collect(toList()));
//
//            } else {
//
//                auth = userService
//                        .findByUsername(username)
//                        .map(InstaUserDetails::new)
//                        .map(userDetails -> {
//
//                            UsernamePasswordAuthenticationToken authentication =
//                                    new UsernamePasswordAuthenticationToken(
//                                            userDetails, null, userDetails.getAuthorities());
//                            authentication
//                                    .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//
//                            return authentication;
//                        })
//                        .orElse(null);
//            }
//
//            SecurityContextHolder.getContext().setAuthentication(auth);
//
//        } else {
//            SecurityContextHolder.clearContext();
//        }
//
//        chain.doFilter(request, response);
//    }
        try {
            String jwt = parseJwt(request);
            if (jwt != null && jwtUtils.validateToken(jwt)) {
                String username = String.valueOf(jwtUtils.getUsernameFromJwtToken(jwt));

                UserDetails userDetails =userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
                        userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
        }

        chain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7, headerAuth.length());
        }

        return null;
    }
}