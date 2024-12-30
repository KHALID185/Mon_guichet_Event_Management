package org.example.Service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.DTO.OrderDTO;
import org.example.DTO.OrderItemDTO;
import org.example.DTO.PurchaseDTO;
import org.example.Entity.Order;
import org.example.Entity.OrderItem;
import org.example.Exception.EventNotFoundException;
import org.example.Exception.InvalidDateRangeException;
import org.example.Exception.InvalidProductIdException;
import org.example.Exception.OrderNotFoundException;
import org.example.Feign.EventServiceClient;
import org.example.Mapper.OrderItemMapper;
import org.example.Mapper.OrderMapper;
import org.example.Repository.OrderDao;
import org.example.Repository.OrderItemDao;
import org.example.Validator.IdValidators;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class OrderService {

    OrderDao orderDao;
    OrderItemDao orderItemDao;
    EventServiceClient eventServiceClient;
    OrderMapper orderMapper;
//    PurchaseServiceIClient purchaseServiceIClient;
//    OrderProducer orderProducer;
    OrderItemMapper orderItemMapper;
    IdValidators idValidators;


    public OrderDTO save(OrderDTO orderDTO) {
        Order order = orderMapper.convertToEntity(orderDTO);

        // Save the order first to generate the orderId
        order = orderDao.save(order);

        BigDecimal totalAmount = BigDecimal.valueOf(0.0);
        List<OrderItem> orderItems = order.getOrderItems();
        for (OrderItem orderItem : orderItems) {
            BigDecimal itemPrice = eventServiceClient.getEventPrice(orderItem.getProductId()).getBody();
            if (itemPrice == null) {
                throw new EventNotFoundException(orderItem.getProductId());
            }
            orderItem.setItemPrice(itemPrice);
            orderItem.setOrderId(order.getOrderId()); // Set the generated orderId
            orderItem.setOrder(order);
            orderItem.setSubtotal(itemPrice.multiply(BigDecimal.valueOf(orderItem.getQuantity())));
            totalAmount = totalAmount.add(orderItem.getSubtotal());
        }
        order.setOrderItems(orderItems);
        order.setTotalAmount(totalAmount);
        order = orderDao.save(order);  // Save again if necessary
        return orderMapper.convertToDTO(order);
    }

//    public List<OrderDTO> findAll() {
//        return orderDao.findAll().stream().map(orderMapper::convertToDTO).collect(Collectors.toList());
//    }

    public List<OrderDTO> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Order> orderPage = orderDao.findAll(pageable);
        return orderPage.stream().map(orderMapper::convertToDTO).collect(Collectors.toList());
    }


    public OrderDTO findById(long orderId) {
        idValidators.validateOrderId(orderId);
        Optional<Order> order = orderDao.findById(orderId);
        if(order.isPresent()) {
            return orderMapper.convertToDTO(order.get());
        }else {
            throw new OrderNotFoundException(orderId);
        }
    }

    public void deleteById(long orderId) {
        idValidators.validateOrderId(orderId);
        if(existsById(orderId)) {
            orderDao.deleteById(orderId);
        }else{
            throw new OrderNotFoundException(orderId);
        }
    }

    public List<OrderDTO> findByCustomerId(long customerId) {
        idValidators.validateCustomerId(customerId);
        return orderDao.findByCustomerId(customerId).stream().map(orderMapper::convertToDTO).collect(Collectors.toList());
    }

    public List<OrderDTO> findByTotalAmountGreaterThan(BigDecimal amount) {
        return orderDao.findByTotalAmountGreaterThan(amount).stream().map(orderMapper::convertToDTO).collect(Collectors.toList());
    }

    public List<OrderDTO> findByOrderDateBetween(LocalDateTime startDate, LocalDateTime endDate) {
        if(startDate.isAfter(endDate)){
            throw new InvalidDateRangeException();
        }
        return orderDao.findByOrderDateBetween(startDate,endDate).stream().map(orderMapper::convertToDTO).collect(Collectors.toList());
    }

    public boolean existsById(long orderId) {
        return orderDao.existsById(orderId);
    }



    @Transactional
    public OrderDTO update(Long orderId, Order updatedOrder) {
        if (!existsById(orderId)) {
            throw new OrderNotFoundException(orderId);
        }

        OrderDTO orderDTO = findById(orderId);
        Order order = orderMapper.convertToEntity(orderDTO);

        if (updatedOrder.getOrderItems() != null) {

            // Set order date to the current date and time
            order.setOrderDate(LocalDateTime.now().truncatedTo(java.time.temporal.ChronoUnit.SECONDS));
            // Delete existing OrderItems
            List<OrderItem> existingOrderItems = orderItemDao.findByOrderId(orderId);
            orderItemDao.deleteAll(existingOrderItems);


            // Update with new OrderItems and calculate total amount
            List<OrderItem> updatedOrderItems = updatedOrder.getOrderItems();
            BigDecimal totalAmount = BigDecimal.ZERO;
            for (OrderItem updatedOrderItem : updatedOrderItems) {
                updatedOrderItem.setOrderId(order.getOrderId());
                updatedOrderItem.setOrder(orderMapper.convertToEntity(findById(orderId)));
                ResponseEntity<BigDecimal> response = eventServiceClient.getEventPrice(updatedOrderItem.getProductId());
                if (response.getStatusCode().equals(HttpStatus.OK)) {
                    BigDecimal itemPrice = response.getBody();
                    updatedOrderItem.setItemPrice(itemPrice);
                    assert itemPrice != null;
                    updatedOrderItem.setSubtotal(itemPrice.multiply(BigDecimal.valueOf(updatedOrderItem.getQuantity())));
                    totalAmount = totalAmount.add(updatedOrderItem.getSubtotal());
                    updatedOrderItem.setOrder(order);
                } else {
                    throw new EventNotFoundException(updatedOrderItem.getProductId());
                }
            }
            order.setOrderItems(updatedOrderItems);
            order.setTotalAmount(totalAmount);
        }

        orderDao.save(order);
        return orderMapper.convertToDTO(order);
    }





//    public List<OrderItemDTO> submitOrder(List<OrderItemDTO> orderItemDTOS){
//        Long orderId = orderItemDTOS.get(0).getOrderId();
//        if(!existsById(orderId))
//            throw new OrderNotFoundException(orderId);
//
//        OrderDTO orderDto = findById(orderId);
//        List<OrderItemDTO> purchasedProducts = new ArrayList<>();
//        List<PurchaseDTO> purchaseDTOS = new ArrayList<>();
//        BigDecimal totalAmount = BigDecimal.valueOf(0.0);
//        for (OrderItemDTO orderItemDTO : orderItemDTOS) {
//            String eventName = eventServiceClient.getEventName(orderItemDTO.getProductId()).getBody();
//            orderItemDTO.setEventName(eventName);
//            if (Boolean.TRUE.equals((eventServiceClient.deductFromStock(orderItemDTO.getProductId(), orderItemDTO.getQuantity())).getBody())) {
//                purchasedProducts.add(orderItemDTO);
////                orderItemDTO.setItemPrice(orderItemDTO.getItemPrice());
//                BigDecimal itemPrice = eventServiceClient.getEventPrice(orderItemDTO.getProductId()).getBody();
//                if (itemPrice == null) {
//                    throw new EventNotFoundException(orderItemDTO.getProductId());
//                }
//                orderItemDTO.setItemPrice(itemPrice);
//                purchaseDTOS.add(new PurchaseDTO(orderItemDTO.getProductId(), orderItemDTO.getQuantity()));
//                totalAmount = totalAmount.add(itemPrice.multiply(BigDecimal.valueOf(orderItemDTO.getQuantity())));
//            }
//        }
//
//        orderDto.setOrderItems(purchasedProducts);
//        orderDto.setTotalAmount(totalAmount);
//        orderDao.save(orderMapper.convertToEntity(orderDto));
//
//
////        purchaseServiceIClient.processPurchasesRequest(purchaseDTOS);
////        orderProducer.sendMessage(orderDto);
//
//        return purchasedProducts;
//
//    }



}