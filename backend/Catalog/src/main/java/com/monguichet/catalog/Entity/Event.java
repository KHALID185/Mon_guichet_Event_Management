package com.monguichet.catalog.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "event")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "created_at")
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "lastUpdate")
    @UpdateTimestamp
    private Date lastUpdate;



    @Column(name = "due_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dueDate;

    @Column(name = "Stock_Ticket")
    private Long Stock_Ticket;
    @Column(name = "price_Ticket")
    private BigDecimal price_Ticket;

    @Column(name = "subCategoryId")
    private long subCategoryId;

    @Column(name = "categoryId")
    private long categoryId;

    @Column(name = "url")
    private String url;
}
