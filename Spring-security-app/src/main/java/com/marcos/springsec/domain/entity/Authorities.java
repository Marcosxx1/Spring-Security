package com.marcos.springsec.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "authorities")
@Table(name = "authorities")
@Data
public class Authorities {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")

    private Customer customer;

    @Column(name = "name", length = 50, nullable = false)
    @JsonBackReference
    private String name;
}
