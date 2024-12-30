package com.monguichet.catalog.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "subcategory")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SubCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    //Check if null
    @Column(name = "name")
    private String name;

    @Column(name = "categoryId")
    private long categoryId;
}
