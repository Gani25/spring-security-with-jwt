package com.gani.spring_security_cmplt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gani.spring_security_cmplt.model.AuthRequest;
import com.gani.spring_security_cmplt.service.JwtService;

@RestController
public class MyController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @GetMapping("/welcome")
    public String showWelcome() {
        return "Welcome to spring demo app";
    }

    @GetMapping("/home")
    public String showHome() {
        return "Home Page";
    }

    @GetMapping("/hi")
    public String showHi() {
        return "Hii Sir";
    }

    @GetMapping("/user")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String showUser() {
        return "Hello USER";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String showAdmin() {
        return "Hello ADMIN";
    }

    @PostMapping("/authenticate")
    public String getTokenByAuth(@RequestBody AuthRequest authRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

        if (authentication.isAuthenticated()) {

            return jwtService.generateToken(authRequest);

        } else {
            throw new UsernameNotFoundException("Invalid user/Credentials please check again!!!");
        }

    }

}
