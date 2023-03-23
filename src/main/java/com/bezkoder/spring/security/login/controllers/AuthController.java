package com.bezkoder.spring.security.login.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.bezkoder.spring.security.login.models.Order;
//import com.bezkoder.spring.security.login.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bezkoder.spring.security.login.models.ERole;
import com.bezkoder.spring.security.login.models.Role;
import com.bezkoder.spring.security.login.models.User;
import com.bezkoder.spring.security.login.payload.request.LoginRequest;
import com.bezkoder.spring.security.login.payload.request.SignupRequest;
import com.bezkoder.spring.security.login.payload.response.UserInfoResponse;
import com.bezkoder.spring.security.login.payload.response.MessageResponse;
import com.bezkoder.spring.security.login.repository.RoleRepository;
import com.bezkoder.spring.security.login.repository.UserRepository;
//import com.bezkoder.spring.security.login.security.jwt.JwtUtils;

@CrossOrigin(origins ="http://localhost:5173")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
//  @Autowired
//  AuthenticationManager authenticationManager;

  @Autowired
  UserRepository userRepository;

  @Autowired
  RoleRepository roleRepository;

//  @Autowired
//  PasswordEncoder encoder;

//  @Autowired
//  JwtUtils jwtUtils;

//  @PostMapping("/signin")
//  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, HttpServletResponse response) {
//    Authentication authentication = authenticationManager.authenticate(
//            new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
//
//    SecurityContextHolder.getContext().setAuthentication(authentication);
//    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
//    ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);
//
//    response.addCookie(jwtCookie.);
//    return  ResponseEntity.ok().header(HttpHeaders.COOKIE, jwtCookie.toString())
//            .body(new UserInfoResponse(userDetails.getUsername(),jwtCookie.toString()));
//  }
  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
//
//    Authentication authentication = authenticationManager
//        .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
//
//    SecurityContextHolder.getContext().setAuthentication(authentication);
//
//    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
//
//    ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);
//
//    List<String> roles = userDetails.getAuthorities().stream()
//        .map(item -> item.getAuthority())
//        .collect(Collectors.toList());

    return ResponseEntity.ok().body(loginRequest.getUsername());
  }


  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
    }

    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
    }

    // Create new user's account
    User user = User.builder().username(signUpRequest.getUsername()).email(signUpRequest.getEmail()).password(signUpRequest.getPassword()).build();
    List<Order> orders = new ArrayList<>();
    Order order1 = Order.builder().user1(user).description("French fries with eqqs cost:10$").build();
    Order order2 = Order.builder().user1(user).description("Spaghetti with meat cost:20$").build();
    Order order3 = Order.builder().user1(user).description("Argentinean Beef with fries cost:100$").build();

    orders.add(order1);
    orders.add(order2);
    orders.add(order3);

    user.setOrders(orders);

    Set<String> strRoles = signUpRequest.getRole();
    Set<Role> roles = new HashSet<>();

    if (strRoles == null) {
      Role userRole = roleRepository.findByName(ERole.ROLE_USER)
          .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
      roles.add(userRole);
    } else {
      strRoles.forEach(role -> {
        switch (role) {
        case "admin":
          Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(adminRole);

          break;
        case "mod":
          Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(modRole);

          break;
        default:
          Role userRole = roleRepository.findByName(ERole.ROLE_USER)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(userRole);
        }
      });
    }

    user.setRoles(roles);
    userRepository.save(user);

    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }

  @PostMapping("/signout")
  public ResponseEntity<?> logoutUser() {
//    ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
    return ResponseEntity.ok()
        .body(new MessageResponse("You've been signed out!"));
  }
}
