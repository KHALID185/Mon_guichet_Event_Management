package org.example.Repository;

import org.example.Entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemDao extends JpaRepository<OrderItem, Long> {

//    @Query("SELECT oi FROM OrderItem oi WHERE oi.orderId = :orderId and oi.productId = :productId")
//    OrderItem findByOrderIdAndProductId(@Param("orderId") Long orderId, @Param("productId") Long productId);

    List<OrderItem> findByOrderId(Long orderId);
}
