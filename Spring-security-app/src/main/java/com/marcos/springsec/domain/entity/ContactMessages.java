package com.marcos.springsec.domain.entity;

import jakarta.persistence.*;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "contact_messages")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContactMessages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contact_id")
    private Long contactId;

    @Column(name = "contact_name", length = 50, nullable = false)
    private String contactName;

    @Column(name = "contact_email", length = 100, nullable = false)
    private String contactEmail;

    @Column(name = "subject", length = 500, nullable = false)
    private String subject;

    @Column(name = "message", length = 2000, nullable = false)
    private String message;

    @Column(name = "create_dt")
    private String createDt;

    @PrePersist
    public void prePersist() {
        this.createDt = java.time.LocalDate.now().toString();
    }
}
