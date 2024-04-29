package org.example.Controller;

import jakarta.annotation.security.DenyAll;
import lombok.AllArgsConstructor;
import org.example.DTO.OrderDTO;
import org.example.DTO.OrderItemDTO;
import org.example.Exception.InvalidCustomerIdException;
import org.example.Exception.InvalidDateRangeException;
import org.example.Exception.InvalidOrderIdException;
import org.example.Exception.OrderNotFoundException;
import org.example.Mapper.OrderMapper;
import org.example.Service.OrderService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/order2")
@AllArgsConstructor
public class OrderController {
    OrderService orderService;
//    PurchaseServiceIClient purchaseServiceIClient;
//    OrderProducer orderProducer;
    OrderMapper orderMapper;


    @PostMapping("/create")
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO) {
        try {
            OrderDTO createdOrder = orderService.save(orderMapper.convertToEntity(orderDTO));
            return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get a list of all orders
    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        try {
            List<OrderDTO> orders = orderService.findAll();
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get a specific order by ID
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable int orderId) {
        try{
            OrderDTO order = orderService.findById(orderId);
            return new ResponseEntity<>(order, HttpStatus.OK);
        } catch (InvalidOrderIdException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (OrderNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update an existing order
    @PutMapping("/{orderId}")
    public ResponseEntity<OrderDTO> updateOrder(@PathVariable int orderId, @RequestBody OrderDTO updatedOrder) {
        try{
            OrderDTO updated = orderService.update(orderId,orderMapper.convertToEntity(updatedOrder));
            return new ResponseEntity<>(updated, HttpStatus.OK);
        }
        catch (InvalidOrderIdException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (OrderNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    // Delete an order by ID
    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable int orderId) {
        try {
            orderService.deleteById(orderId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (OrderNotFoundException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<OrderDTO>> getOrdersByCustomerId(@PathVariable int customerId) {
        try {
            List<OrderDTO> orders = orderService.findByCustomerId(customerId);
            return new ResponseEntity<>(orders, HttpStatus.OK);
        }catch (InvalidCustomerIdException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get orders by order status
//    @GetMapping("/status/{orderStatus}")
//    public ResponseEntity<List<OrderDTO>> getOrdersByStatus(@PathVariable Status orderStatus) {
//        try {
//            List<OrderDTO> orders = orderService.findByOrderStatus(orderStatus);
//            return new ResponseEntity<>(orders, HttpStatus.OK);
//        }catch (Exception e){
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    // Get orders with a total amount greater than a specified value
    @GetMapping("/totalAmountGreaterThan/{amount}")
    public ResponseEntity<List<OrderDTO>> getOrdersWithTotalAmountGreaterThan(@PathVariable BigDecimal amount) {
        try {
            List<OrderDTO> orders = orderService.findByTotalAmountGreaterThan(amount);
            return new ResponseEntity<>(orders, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get orders within a date range
    @GetMapping("/dateRange")
    public ResponseEntity<List<OrderDTO>> getOrdersWithinDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime endDate
    ) {
        try {
            List<OrderDTO> orders = orderService.findByOrderDateBetween(startDate, endDate);
            return new ResponseEntity<>(orders, HttpStatus.OK);
        }catch (InvalidDateRangeException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @PostMapping("/submit")
    public ResponseEntity<List<OrderItemDTO>> submitOrder(@RequestBody List<OrderItemDTO> orderItemDTOS){
        return new ResponseEntity<>(orderService.submitOrder(orderItemDTOS), HttpStatus.OK);
    }

}
