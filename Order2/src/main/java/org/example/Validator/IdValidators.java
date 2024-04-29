package org.example.Validator;

import lombok.NoArgsConstructor;
import org.example.Exception.InvalidCustomerIdException;
import org.example.Exception.InvalidOrderIdException;
import org.springframework.stereotype.Component;


@Component
@NoArgsConstructor
public class IdValidators {

    public void validateOrderId(Integer orderId){
        if(orderId <= 0) throw new InvalidOrderIdException("Order id is Invalid");
    }

    public void validateCustomerId(Integer customerId){
        if(customerId <= 0) throw new InvalidCustomerIdException("Customer id is Invalid");
    }

}
