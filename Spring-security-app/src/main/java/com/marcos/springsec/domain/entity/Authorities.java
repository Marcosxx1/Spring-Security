package com.marcos.springsec.domain.entity;

import jakarta.persistence.*;

@Entity(name = "authorities")
public class Authorities {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    private Customer customer;

    @Column(name = "name", length = 50, nullable = false)
    private String name;


}
