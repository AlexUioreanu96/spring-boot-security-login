package com.alex.demo.security.services;


import com.alex.demo.models.Order;
import com.alex.demo.models.User;
import com.alex.demo.repository.OrderRepository;
import com.alex.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrdersService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;

    public void prepopulateOrdersForCurrentUser(User user) {
        if (userRepository.findById(user.getId()).isPresent()) {
            User user1 = userRepository.findById(user.getId()).get();

            // Create some sample orders for the current user
            List<Order> orders = new ArrayList<>();
            Order order1 = Order.builder().user1(user1).description("French fries with eqqs cost:10$").build();
            Order order2 = Order.builder().user1(user1).description("Spaghetti with meat cost:20$").build();
            Order order3 = Order.builder().user1(user1).description("Argentinean Beef with fries cost:100$").build();

            orders.add(order1);
            orders.add(order2);
            orders.add(order3);
            user.setOrders(orders);

            userRepository.save(user);
        }
    }

    public List<Order> findByUserId(Long id) {
        return orderRepository.findByUser1Id(id);
    }

    public Order save(Order order) {
        orderRepository.save(order);
        return order;
    }
}
