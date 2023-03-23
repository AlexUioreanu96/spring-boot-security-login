package com.bezkoder.spring.security.login.security.services;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bezkoder.spring.security.login.models.User;
import com.bezkoder.spring.security.login.repository.UserRepository;

@Service
public class UserDetailsServiceImpl {
  @Autowired
  UserRepository userRepository;

//  @Transactional
//  public UserDetailsImpl loadUserByUsername(String username) t {
//    User user = userRepository.findByUsername(username)
//        .orElseThrow();
//
//    return UserDetailsImpl.build(user);
//  }

}
