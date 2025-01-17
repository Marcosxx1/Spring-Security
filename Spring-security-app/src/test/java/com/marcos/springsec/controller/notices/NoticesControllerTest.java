package com.marcos.springsec.controller.notices;

import com.marcos.springsec.domain.entity.NoticeDetails;
import com.marcos.springsec.service.notices.NoticeService;
import com.marcos.springsec.service.util.notice.NoticeBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class NoticesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private NoticeService noticeService;

    @Test
    void testGetNotices_whenActiveNoticesExist_thenReturnNoticesWithStatus200() throws Exception {
        List<NoticeDetails> activeNotices = NoticeBuilder.listOfNotice(2);

        List<NoticeDetails> expectedActiveNotices = activeNotices.stream()
                .filter(n ->
                        n.getNoticBegDt().isBefore(LocalDate.now()) &&
                        n.getNoticEndDt().isAfter(LocalDate.now()))
                .toList();

        when(noticeService.findAllActiveNotices()).thenReturn(expectedActiveNotices);

        mockMvc.perform(get("/notices").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].noticeId").value(expectedActiveNotices.getFirst().getNoticeId()))
                .andExpect(jsonPath("$[0].noticeSummary").value(expectedActiveNotices.getFirst().getNoticeSummary()));
    }
}
