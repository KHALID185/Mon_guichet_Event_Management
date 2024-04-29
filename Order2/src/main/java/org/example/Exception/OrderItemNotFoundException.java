package org.example.Exception;

public class OrderItemNotFoundException extends RuntimeException {
    public OrderItemNotFoundException(int id) {
        super("Order Item with id: "+ id +" is not found");
    }
}
