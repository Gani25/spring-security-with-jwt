package com.gani.spring_security_cmplt.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.gani.spring_security_cmplt.entity.UserInfo;

public class CustomUserDetails implements UserDetails {

    // private UserInfo userInfo;
    private String username;
    private String password;
    private List<GrantedAuthority> authorities;

    public CustomUserDetails(UserInfo userInfo) {
        this.username = userInfo.getName();
        this.password = userInfo.getPassword();
        this.authorities = Arrays
                .stream(userInfo.getRoles().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        /*
         * String rolesString = userInfo.getRoles();
         * String[] rolesArray = rolesString.split(",");
         * 
         * List<GrantedAuthority> authorities = new ArrayList<>();
         * for (String role : rolesArray) {
         * authorities.add(new SimpleGrantedAuthority(role.trim()));
         * }
         * this.authorities = authorities;
         */

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return UserDetails.super.isEnabled();
    }

}
