package com.marcos.springsec.service.notices;

import com.marcos.springsec.domain.entity.NoticeDetails;
import com.marcos.springsec.repository.notice.NoticeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class NoticeServiceImplTest {

    @MockitoBean
    private NoticeRepository noticeRepository;

    @Autowired
    private NoticeServiceImpl noticeService;

    @Test
    void testFindAllActiveNotices_whenThereAreActiveNotices_thenReturnAllActiveNotices() {
         List<NoticeDetails> activeNotices = List.of(NoticeDetails.builder().build());

         when(noticeRepository.findByNoticBegDtLessThanEqualAndNoticEndDtGreaterThanEqual(LocalDate.now()))
                .thenReturn(Optional.of(activeNotices));

         List<NoticeDetails> result = noticeService.findAllActiveNotices();

         assertEquals(activeNotices, result);
    }
}

