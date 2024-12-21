package com.monguichet.catalog.Entity.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventResponseDto {

    private Long id;
    private String name;
    private String description;
    private Date createdAt;
    private Date lastUpdate;
    private Date dueDate;

    private String url;
    private Long Stock_Ticket;
    private long subCategoryId;
    private long categoryId;
    private BigDecimal price_Ticket;

}
