package com.marcos.springsec.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity(name = "notice_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "notice_details")
@EqualsAndHashCode
public class NoticeDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    private Long noticeId;

    @Column(name = "notice_summary", length = 200, nullable = false)
    private String noticeSummary;

    @Column(name = "notice_details", length = 500, nullable = false)
    private String noticeDetails;

    @Column(name = "notic_beg_dt", nullable = false)
    private LocalDate noticBegDt; // <-

    @Column(name = "notic_end_dt")
    private LocalDate noticEndDt; // <-

    @Column(name = "create_dt")
    private LocalDate createDt;

    @Column(name = "update_dt")
    private LocalDate updateDt;

    @PrePersist
    public void prePersist() {
        this.createDt = LocalDate.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updateDt = LocalDate.now();
    }
}
