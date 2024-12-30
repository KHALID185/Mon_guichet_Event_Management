package org.example.Service;

import lombok.AllArgsConstructor;
import org.example.DTO.OrderItemDTO;
import org.example.Entity.OrderItem;
import org.example.Exception.EventNotFoundException;
import org.example.Exception.InvalidProductIdException;
import org.example.Exception.OrderItemNotFoundException;
import org.example.Feign.EventServiceClient;
import org.example.Mapper.OrderItemMapper;
import org.example.Mapper.OrderMapper;
import org.example.Repository.OrderItemDao;
import org.example.Validator.IdValidators;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderItemService {
    private final OrderItemDao orderItemDao;
    private final OrderItemMapper orderItemMapper;
    private final OrderService orderService;
   private final EventServiceClient eventServiceClient;
    private final OrderMapper orderMapper;
    private final IdValidators idValidators;




    public OrderItemDTO getOrderItemById(Long id) {
        Optional<OrderItem> orderItem = orderItemDao.findById(id);
        if(orderItem.isPresent()){
            return orderItemMapper.convertToDTO(orderItem.get());
        }else {
            throw new OrderItemNotFoundException(id);
        }
    }

    public void deleteOrderItem(Long id) {
        Optional<OrderItem> orderItem = orderItemDao.findById(id);
        if(orderItem.isPresent()){
            orderItemDao.deleteById(id);
        }else {
            throw new OrderItemNotFoundException(id);
        }
    }


    public OrderItemDTO updateOrderItem(Long id, OrderItem updatedOrderItem) {
        Optional<OrderItem> existingOrderItem = orderItemDao.findById(id);
        if (existingOrderItem.isPresent()) {
            OrderItem orderItem = existingOrderItem.get();
            orderItem.setProductId(updatedOrderItem.getProductId());
            orderItem.setQuantity(updatedOrderItem.getQuantity());
            // Retrieve the item price from the catalog microservice
            BigDecimal itemPrice = eventServiceClient.getEventPrice(updatedOrderItem.getProductId()).getBody();
            if (itemPrice == null) {
                throw new EventNotFoundException(updatedOrderItem.getProductId());
            }
            orderItem.setItemPrice(itemPrice);
            return orderItemMapper.convertToDTO(orderItemDao.save(orderItem));
        } else {
            throw new OrderItemNotFoundException(id);
        }
    }

    public List<OrderItemDTO> findByOrderId(Long orderId) {
        idValidators.validateOrderId(orderId);
        List<OrderItem> orderItemList = orderItemDao.findByOrderId(orderId);
        return orderItemList.stream().map(orderItemMapper::convertToDTO).collect(Collectors.toList());
    }
}