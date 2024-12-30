package org.example.DTO;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderDTO {
    private Long orderId;
    private Long customerId;
    private LocalDateTime orderDate;
    private BigDecimal totalAmount;
    //   private Status orderStatus;
    private List<OrderItemDTO> orderItems;

}