package org.example.Mapper;

import org.example.DTO.OrderDTO;
import org.example.Entity.Order;
import org.example.Entity.OrderItem;
import org.example.Exception.EventNotFoundException;
import org.example.Feign.EventServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

@Autowired
private OrderItemMapper orderItemMapper;



public OrderDTO convertToDTO(Order order) {
    if (order == null)
        return null;

    return OrderDTO.builder()
            .orderId(order.getOrderId())
            .customerId(order.getCustomerId())
            .orderDate(order.getOrderDate())
            .totalAmount(order.getTotalAmount())
//            .orderStatus(order.getOrderStatus())
            .orderItems(Optional.ofNullable(order.getOrderItems())
                    .orElse(Collections.emptyList())
                    .stream()
                    .map(orderItemMapper::convertToDTO)
                    .collect(Collectors.toList()))
            .build();
}



public Order convertToEntity(OrderDTO orderDTO) {
    if (orderDTO == null)
        return null;

    Order order = new Order();
    if (orderDTO.getOrderId() != null) {
        order.setOrderId(orderDTO.getOrderId());
    }
    order.setCustomerId(orderDTO.getCustomerId());
    order.setOrderDate(orderDTO.getOrderDate());
    order.setTotalAmount(orderDTO.getTotalAmount());
//    order.setOrderStatus(orderDTO.getOrderStatus());

    List<OrderItem> orderItems = Optional.ofNullable(orderDTO.getOrderItems())
            .orElse(Collections.emptyList())
            .stream()
            .map(orderItemMapper::convertToEntity)
            .collect(Collectors.toList());

    order.setOrderItems(orderItems);

    return order;
}

}