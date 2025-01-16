package com.marcos.springsec.controller.notices;

import com.marcos.springsec.domain.entity.NoticeDetails;
import com.marcos.springsec.service.notices.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequiredArgsConstructor
public class NoticesControllerImpl implements NoticesController {

    private final NoticeService noticesService;

    @Override
    @GetMapping("/notices")
    public ResponseEntity<List<NoticeDetails>> getNotices() {

        List<NoticeDetails> activeNotices = noticesService.findAllActiveNotices();

        if (activeNotices == null || activeNotices.isEmpty()) {
            return null; //ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS))
                .body(activeNotices);
    }
}
