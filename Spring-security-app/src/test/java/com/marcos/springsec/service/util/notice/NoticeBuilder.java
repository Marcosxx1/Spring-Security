package com.marcos.springsec.service.util.notice;

import com.marcos.springsec.domain.entity.NoticeDetails;

import java.time.LocalDate;
import java.util.List;

public class NoticeBuilder {

    private NoticeBuilder() {
    }

    public static List<NoticeDetails> listOfNotice(int quantity){
        return List.of(
                 NoticeDetails.builder()
                        .noticeId(1L)
                        .noticeSummary("Active Summary 1")
                        .noticeDetails("Active Details 1")
                        .noticBegDt(LocalDate.now().minusDays(1)) // Data válida
                        .noticEndDt(LocalDate.now().plusDays(1))  // Data válida
                        .build(),
                 NoticeDetails.builder()
                        .noticeId(2L)
                        .noticeSummary("Inactive Summary")
                        .noticeDetails("Inactive Details")
                        .noticBegDt(LocalDate.now().minusDays(10)) // Fora do intervalo
                        .noticEndDt(LocalDate.now().minusDays(5))  // Fora do intervalo
                        .build()
        );
    }
}
