package com.marcos.springsec.service.notices;

import com.marcos.springsec.domain.entity.NoticeDetails;
import com.marcos.springsec.repository.notice.NoticeRepository;
import com.marcos.springsec.service.util.notice.NoticeBuilder;
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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class NoticeServiceImplTest {

    @MockitoBean
    private NoticeRepository noticeRepository;

    @Autowired
    private NoticeServiceImpl noticeService;

    @Test
    void testFindAllActiveNotices_whenThereAreActiveAndInactiveNotices_thenReturnOnlyActiveNotices() {
        List<NoticeDetails> notices = NoticeBuilder.listOfNotice(2);

        // Filtra apenas os avisos ativos para simular o comportamento esperado do repositório
        List<NoticeDetails> activeNotices = notices.stream()
                .filter(n -> n.getNoticBegDt().isBefore(LocalDate.now()) &&
                        n.getNoticEndDt().isAfter(LocalDate.now()))
                .toList();

        when(noticeRepository.findNoticesByCurrentDate(LocalDate.now()))
                .thenReturn(Optional.of(activeNotices));

        List<NoticeDetails> result = noticeService.findAllActiveNotices();

        assertEquals(activeNotices, result);
        verify(noticeRepository, times(1)).findNoticesByCurrentDate(LocalDate.now());
    }

    @Test
    void testFindAllActiveNotices_whenThereAreNoActiveAndInactiveNotices_thenReturnNull() {
        when(noticeRepository.findNoticesByCurrentDate(LocalDate.now()))
                .thenReturn(Optional.empty());

        List<NoticeDetails> result = noticeService.findAllActiveNotices();

        assertNull(result);
        verify(noticeRepository, times(1)).findNoticesByCurrentDate(LocalDate.now());
    }

}

