package com.bezkoder.spring.security.login.repository;

import com.bezkoder.spring.security.login.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser1Id(Long userId);
}
