package org.example.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {
    private Long orderItemId;
    private String eventName;
    private Long orderId;
    private Long productId;
    private Long quantity;
    private BigDecimal itemPrice;
    private BigDecimal subtotal;

}
