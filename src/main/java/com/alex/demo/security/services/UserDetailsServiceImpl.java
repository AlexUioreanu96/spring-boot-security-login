package com.alex.demo.security.services;

import com.alex.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
