package com.had.depressiontherapyappbackend.controllers;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.had.depressiontherapyappbackend.payloads.JwtAuthRequest;
import com.had.depressiontherapyappbackend.payloads.JwtAuthResponse;
import com.had.depressiontherapyappbackend.security.JwtTokenHelper;

@RestController
@RequestMapping(path = "/auth")
public class AuthController {

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private UserDetailsService userDetailsService;
    
    @Autowired
    private AuthenticationManager authenticationManager;

    //@RequestMapping(value = "/login", method = {RequestMethod.OPTIONS, RequestMethod.POST})
    @PostMapping(path = "/login")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody JwtAuthRequest authRequest) throws Exception {
        String email = authRequest.getEmail();
        String password = authRequest.getPassword();

        this.authenticate(email, password);

        UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);

        String jwtToken = this.jwtTokenHelper.generateToken(userDetails); 

        JwtAuthResponse authResponse = new JwtAuthResponse();
        authResponse.setToken(jwtToken);

        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    private UsernamePasswordAuthenticationToken createToken(String email, String password) {
        return new UsernamePasswordAuthenticationToken(email, password);
    }

    private void authenticate(String email, String password) throws Exception {
        UsernamePasswordAuthenticationToken authenticationToken = createToken(email, password);

        try {
            this.authenticationManager.authenticate(authenticationToken);
        } catch(BadCredentialsException ex) {
            System.out.println("Incorrect Credentials!");
            throw new Exception("Incorrect Credentials!");
        }
    }
}
