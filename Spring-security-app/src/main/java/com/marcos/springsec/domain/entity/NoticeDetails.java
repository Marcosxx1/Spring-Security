package com.marcos.springsec.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "notice_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoticeDetails {

    /*CREATE TABLE `notice_details`
(
    `notice_id`      int          NOT NULL AUTO_INCREMENT,
    `notice_summary` varchar(200) NOT NULL,
    `notice_details` varchar(500) NOT NULL,
    `notic_beg_dt`   date         NOT NULL,
    `notic_end_dt`   date DEFAULT NULL,
    `create_dt`      date DEFAULT NULL,
    `update_dt`      date DEFAULT NULL,
    PRIMARY KEY (`notice_id`)
);
*/

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    private Long noticeId;

    @Column(name = "notice_summary", length = 200, nullable = false)
    private String noticeSummary;

    @Column(name = "notice_details", length = 500, nullable = false)
    private String noticeDetails;

    @Column(name = "notic_beg_dt", nullable = false)
    private String noticBegDt;

    @Column(name = "notic_end_dt")
    private String noticEndDt;

    @Column(name = "create_dt")
    private String createDt;

    @Column(name = "update_dt")
    private String updateDt;

    @PrePersist
    public void prePersist() {
        this.createDt = java.time.LocalDate.now().toString();
    }

    @PreUpdate
    public void preUpdate() {
        this.updateDt = java.time.LocalDate.now().toString();
    }

}
