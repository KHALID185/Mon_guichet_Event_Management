package org.example.Mapper;

import org.example.DTO.OrderItemDTO;
import org.example.Entity.OrderItem;
import org.example.Exception.EventNotFoundException;
import org.example.Feign.EventServiceClient;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class OrderItemMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private EventServiceClient eventServiceClient;


    static {
        modelMapper.addMappings(new PropertyMap<OrderItem, OrderItemDTO>() {
            @Override
            protected void configure() {
                // Map RentalItem's Rental's RentalId to RentalItemResponseDto's RentalId
                map().setOrderId(source.getOrder().getOrderId());
            }
        });

    }
    public OrderItemDTO convertToDTO(OrderItem orderItem) {
        return OrderItemDTO.builder()
                .orderItemId(orderItem.getOrderItemId())
                .orderId(orderItem.getOrderId())
                .productId(orderItem.getProductId())
                .quantity(orderItem.getQuantity())
                .itemPrice(orderItem.getItemPrice())
                .subtotal(orderItem.getSubtotal())
                .eventName(getEventName(orderItem.getProductId()))
                .build();
    }

    private String getEventName(Long productId) {
        // Call the getEventName method from the EventServiceClient
        return eventServiceClient.getEventName(productId).getBody();
    }

    public OrderItem convertToEntity(OrderItemDTO orderItemDTO) {
        if (orderItemDTO == null)
            return null;

        OrderItem orderItem = new OrderItem();
        orderItem.setOrderId(orderItemDTO.getOrderId());
        orderItem.setProductId(orderItemDTO.getProductId());
        orderItem.setQuantity(orderItemDTO.getQuantity());
        orderItem.setSubtotal(orderItemDTO.getSubtotal());
//        orderItem.setItemPrice(orderItemDTO.getItemPrice());
        return orderItem;
    }

    public List<OrderItemDTO> convertToDTO(List<OrderItem> orderItems){
        return Optional.ofNullable(orderItems)
                .map(list -> list.stream()
                        .map(orderItem -> OrderItemDTO.builder()
                                .orderId(orderItem.getOrderId())
                                .productId(orderItem.getProductId())
                                .quantity(orderItem.getQuantity())
                                .itemPrice(orderItem.getItemPrice())
                                .subtotal(orderItem.getSubtotal())
                                .build())
                        .collect(Collectors.toList()))
                .orElse(null);
    }


}
