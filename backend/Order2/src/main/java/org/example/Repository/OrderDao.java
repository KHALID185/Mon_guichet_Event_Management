package org.example.Repository;

import org.example.DTO.OrderDTO;
import org.example.Entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderDao extends JpaRepository<Order, Long> {
    List<Order> findByCustomerId(long customerId);

    List<Order> findByTotalAmountGreaterThan(BigDecimal amount);

    List<Order> findByOrderDateBetween(LocalDateTime orderDate, LocalDateTime orderDate2);

    // Add pagination support
    Page<Order> findAll(Pageable pageable);
}
