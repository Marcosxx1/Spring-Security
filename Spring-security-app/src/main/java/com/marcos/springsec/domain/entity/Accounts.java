package com.marcos.springsec.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity(name = "accounts")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Accounts {

    @Id
    @Column(name = "account_number")
    private Long accountNumber;

    @Column(name = "account_type", length = 100, nullable = false)
    private String accountType;

    @Column(name = "branch_address", length = 200, nullable = false)
    private String branchAddress;

    @Column(name = "create_dt")
    private String createDt;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    private Customer customer;
}

