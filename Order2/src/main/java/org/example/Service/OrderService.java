package org.example.Service;

import lombok.AllArgsConstructor;
import org.example.DTO.OrderDTO;
import org.example.DTO.OrderItemDTO;
import org.example.DTO.PurchaseDTO;
import org.example.Entity.Order;
import org.example.Exception.InvalidDateRangeException;
import org.example.Exception.OrderNotFoundException;
import org.example.Mapper.OrderItemMapper;
import org.example.Mapper.OrderMapper;
import org.example.Repository.OrderDao;
import org.example.Repository.OrderItemDao;
import org.example.Validator.IdValidators;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderService {

    OrderDao orderDao;
    OrderItemDao orderItemDao;
//    InventoryServiceClient inventoryServiceClient;
    OrderMapper orderMapper;
//    PurchaseServiceIClient purchaseServiceIClient;
//    OrderProducer orderProducer;
    OrderItemMapper orderItemMapper;
    IdValidators idValidators;


    public OrderDTO save(Order order) {
        return orderMapper.convertToDTO(orderDao.save(order));
    }

    public List<OrderDTO> findAll() {
        return orderDao.findAll().stream().map(orderMapper::convertToDTO).collect(Collectors.toList());
    }

    public OrderDTO findById(int orderId) {
        idValidators.validateOrderId(orderId);
        Optional<Order> order = orderDao.findById(orderId);
        if(order.isPresent()) {
            return orderMapper.convertToDTO(order.get());
        }else {
            throw new OrderNotFoundException(orderId);
        }
    }

    public void deleteById(int orderId) {
        idValidators.validateOrderId(orderId);
        if(existsById(orderId)) {
            orderDao.deleteById(orderId);
        }else{
            throw new OrderNotFoundException(orderId);
        }
    }

    public List<OrderDTO> findByCustomerId(int customerId) {
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

    public boolean existsById(int orderId) {
        return orderDao.existsById(orderId);
    }




    public OrderDTO update(int orderId, Order updatedOrder) {
        if(!existsById(orderId))
            throw new OrderNotFoundException(orderId);

        OrderDTO orderDTO = findById(orderId);
        Order order = orderMapper.convertToEntity(orderDTO);
        if(updatedOrder.getOrderItems()!=null)
            order.setOrderItems(updatedOrder.getOrderItems());
        if(updatedOrder.getTotalAmount()!=null)
            order.setTotalAmount(updatedOrder.getTotalAmount());
        save(order);
        return orderMapper.convertToDTO(order);
    }

    public List<OrderItemDTO> submitOrder(List<OrderItemDTO> orderItemDTOS){
        Integer orderId = orderItemDTOS.get(0).getOrderId();
        if(!existsById(orderId))
            throw new OrderNotFoundException(orderId);

        OrderDTO orderDto = findById(orderId);
        List<OrderItemDTO> purchasedProducts = new ArrayList<>();
        List<PurchaseDTO> purchaseDTOS = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.valueOf(0.0);
        for (OrderItemDTO orderItemDTO : orderItemDTOS) {
//            if (Boolean.TRUE.equals((inventoryServiceClient.deductFromStock(orderItemDTO.getProductId(), orderItemDTO.getQuantity())).getBody())) {
                purchasedProducts.add(orderItemDTO);
                orderItemDTO.setItemPrice(orderItemDTO.getItemPrice());
                purchaseDTOS.add(new PurchaseDTO(orderItemDTO.getProductId(), orderItemDTO.getQuantity()));
                totalAmount = totalAmount.add(orderItemDTO.getItemPrice().multiply(BigDecimal.valueOf(orderItemDTO.getQuantity())));
//            }
        }

        orderDto.setOrderItems(purchasedProducts);
        orderDto.setTotalAmount(totalAmount);
        orderDao.save(orderMapper.convertToEntity(orderDto));


//        purchaseServiceIClient.processPurchasesRequest(purchaseDTOS);
//        orderProducer.sendMessage(orderDto);

        return purchasedProducts;

    }



}