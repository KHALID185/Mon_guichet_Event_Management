package org.example.Exception;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(long orderId) {
        super("Order with order id: "+ orderId+" is not found");
    }
}
