package com.bezkoder.spring.security.login.controllers;

import com.bezkoder.spring.security.login.models.Order;
import com.bezkoder.spring.security.login.models.User;
import com.bezkoder.spring.security.login.security.services.OrdersService;
import com.bezkoder.spring.security.login.security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@EnableAutoConfiguration
@ComponentScan
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrdersService ordersService;
    @Autowired
    private UserService userService;

    @GetMapping("/{username}")
    public ResponseEntity<List<Order>> getOrders(@PathVariable String username) {
        User user = userService.findByUsername(username);
        List<Order> orders = ordersService.findByUserId(user.getId());
        return ResponseEntity.ok(orders);
    }

    @PostMapping("/{username}")
    public ResponseEntity<?> createOrder(@RequestBody String description, @PathVariable String username) {
        User user = userService.findByUsername(username);
        Order order = Order.builder().user1(user).description(description).build();
        ordersService.save(order);
        return ResponseEntity.ok("Order created successfully");
    }

    @DeleteMapping("/{username}/{orderid}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long orderid, @PathVariable String username) {
        User user = userService.findByUsername(username);
        Optional<Order> orderOptional = user.getOrders().stream().filter(order -> order.getId().equals(orderid)).findFirst();
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            user.removeOrder(order);
            userService.save(user);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{username}/{orderId}")
    public ResponseEntity<Order> updateOrder(@PathVariable("orderId") Long orderId, @PathVariable String username, @RequestBody String description) {
        User user = userService.findByUsername(username);
        Optional<Order> orderOptional = user.getOrders().stream().filter(order -> order.getId().equals(orderId)).findFirst();
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            order.setDescription(description);
            Order savedOrder = ordersService.save(order);
            return ResponseEntity.ok(savedOrder);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
