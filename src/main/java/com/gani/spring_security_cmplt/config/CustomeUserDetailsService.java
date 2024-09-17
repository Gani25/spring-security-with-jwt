package com.gani.spring_security_cmplt.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.gani.spring_security_cmplt.entity.UserInfo;
import com.gani.spring_security_cmplt.repository.UserInfoRepository;

@Component
public class CustomeUserDetailsService implements UserDetailsService {

    @Autowired
    private UserInfoRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> optional = repository.findByName(username);
        UserInfo userInfo = null;

        // return optional
        // .map(CustomUserDetails::new)
        // .orElseThrow(() -> new UsernameNotFoundException("user not found"));

        if (optional.isEmpty()) {
            throw new UsernameNotFoundException("user not found");
        } else {
            userInfo = optional.get();
            return new CustomUserDetails(userInfo);
        }
    }

}
