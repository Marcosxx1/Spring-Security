package com.marcos.springsec.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "account_transactions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountTransactions {

    @Id
    @Column(name = "transaction_id", length = 200, nullable = false)
    private String transactionId;

    @Column(name = "transaction_dt", nullable = false)
    private String transactionDt;

    @Column(name = "transaction_summary", length = 200, nullable = false)
    private String transactionSummary;

    @Column(name = "transaction_type", length = 100, nullable = false)
    private String transactionType;

    @Column(name = "transaction_amt", nullable = false)
    private int transactionAmt;

    @Column(name = "closing_balance", nullable = false)
    private int closingBalance;

    @Column(name = "create_dt")
    private String createDt;

    @ManyToOne
    @JoinColumn(name = "account_number", referencedColumnName = "account_number")
    private Accounts account;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    private Customer customer;

}
