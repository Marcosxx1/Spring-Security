package com.marcos.springsec.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "loans")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Loans {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loan_number", nullable = false)
    private Long loanNumber;

    @Column(name = "start_dt", nullable = false)
    private String startDt;

    @Column(name = "loan_type", length = 100, nullable = false)
    private String loanType;

    @Column(name = "total_loan", nullable = false)
    private int totalLoan;

    @Column(name = "amount_paid", nullable = false)
    private int amountPaid;

    @Column(name = "outstanding_amount", nullable = false)
    private int outstandingAmount;

    @Column(name = "create_dt")
    private String createDt;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    private Customer customer;

    @PrePersist
    protected void onCreate() {
        createDt = new java.util.Date().toString();
    }

}
