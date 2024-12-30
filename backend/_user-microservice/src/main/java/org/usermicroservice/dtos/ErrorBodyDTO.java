package org.usermicroservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import java.util.Date;
import java.util.List;

@AllArgsConstructor @NoArgsConstructor @Setter @Getter
public class ErrorBodyDTO {
    private Date timestamp;
    private HttpStatus status;
    private List<String> errors;
}