package com.had.depressiontherapyappbackend.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        final int JWT_TOKEN_START_INDEX = 7;
        String username = null;
        String token = null;
        String requestHeader = request.getHeader("Authorization");

        if(requestHeader != null && requestHeader.startsWith("Bearer")) {

            token = requestHeader.substring(JWT_TOKEN_START_INDEX);

            try{
                username = this.jwtTokenHelper.getUsernameFromToken(token);
            } catch(IllegalArgumentException e) {
                System.out.println("IllegalArgumentException : Unable to access token!");
            } catch(ExpiredJwtException e) {
                System.out.println("ExpiredJwtException : Jwt token expired!");
            } catch(MalformedJwtException e) {
                System.out.println("MalformedJwtException : Jwt token invalid/malformed!");
            }
        }
        else {
            System.out.println("Request Header is null");
        }

        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            if(this.jwtTokenHelper.validateToken(token, userDetails)) {

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken;

                usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, 
                                                                null, 
                                                                userDetails.getAuthorities()
                                                        );
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
            else {
                System.out.println("Invalid token!");
            }
        }
        else {
            System.out.println("Username is null!");
            //throw new BadCredentialsException("Invalid Username!");
        }

        filterChain.doFilter(request, response);
    }
    
}
