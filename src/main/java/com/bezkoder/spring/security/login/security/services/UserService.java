package com.bezkoder.spring.security.login.security.services;


import com.bezkoder.spring.security.login.models.Order;
import com.bezkoder.spring.security.login.models.User;
import com.bezkoder.spring.security.login.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void createDefaultUser() {
        if (getAllUsers().isEmpty()) {
            User user = User.builder().id(1L).email("alexandru.uioreanu@gmail.com").password("test").username("Alex").build();

            List<Order> orders = new ArrayList<>();
            Order order1 = Order.builder().user1(user).description("French fries with eqqs cost:10$").build();
            Order order2 = Order.builder().user1(user).description("Spaghetti with meat cost:20$").build();
            Order order3 = Order.builder().user1(user).description("Argentinean Beef with fries cost:100$").build();

            orders.add(order1);
            orders.add(order2);
            orders.add(order3);

            user.setOrders(orders);

            save(user);
        }
    }

    public User findByUsername(String username) {
       if(userRepository.findByUsername(username).isPresent()) {
            return userRepository.findByUsername(username).get();
        }
       return User.builder().build();
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
