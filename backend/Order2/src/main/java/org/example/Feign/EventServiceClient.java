package org.example.Feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient("catalog-microservice")
public interface EventServiceClient {
    @PutMapping("/api/v1/category/subcategory/event/deduct")
    ResponseEntity<Boolean> deductFromStock(@RequestParam Long id, @RequestParam Long quantity);

    @GetMapping("/api/v1/category/subcategory/event/price/{id}")
    ResponseEntity<BigDecimal> getEventPrice(@PathVariable Long id);

    @GetMapping("/api/v1/category/subcategory/event/name/{id}")
    ResponseEntity<String> getEventName(@PathVariable Long id);


}
