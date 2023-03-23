//package com.bezkoder.spring.security.login.controllers;
//
//import com.example.RestaurantOrdersSystem2.model.User;
//import com.example.RestaurantOrdersSystem2.service.OrdersService;
//import com.example.RestaurantOrdersSystem2.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.bind.annotation.*;
//
//import java.security.Principal;
//
//@RestController
//@EnableAutoConfiguration
//@ComponentScan
//@RequestMapping("/api/users")
//public class UserController {
//    @Autowired
//    private UserService userService;
//    @Autowired
//    private OrdersService ordersService;
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @PostMapping("/signup")
//    public ResponseEntity<?> signUp(@RequestBody User user) {
//        if (userService.findByUsername(user.getUsername()) != null) {
//            return ResponseEntity.badRequest().body("Username is already taken");
//        }
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        userService.save(user);
//        ordersService.prepopulateOrdersForCurrentUser(user);
//        return ResponseEntity.ok("User registered successfully");
//    }
//
//    @GetMapping("/profile")
//    public ResponseEntity<User> profile(Principal principal) {
//        User user = userService.findByUsername(principal.getName());
//        return ResponseEntity.ok(user);
//    }
//}