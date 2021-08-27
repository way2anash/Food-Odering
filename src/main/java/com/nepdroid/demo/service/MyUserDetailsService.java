package com.nepdroid.demo.service;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nepdroid.demo.model.Customer;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private CustomerService customerService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    	
        Customer user = customerService.findCusotomerByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("Email %s not found", email));
        }
        
        return new User(user.getEmail(), user.getPassword(),
        		new HashSet<GrantedAuthority>());
    }
    

}