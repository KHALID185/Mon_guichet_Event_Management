package org.example.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record PurchaseDTO(
        @NotNull(message = "productId cannot be null")
        Long productId,
        @Min(value = 0, message = "quantity cannot be less than 0")
        Long quantity
) {
}