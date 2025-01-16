package com.marcos.springsec.controller.notices;

import com.marcos.springsec.domain.entity.NoticeDetails;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface NoticesController {

    ResponseEntity<List<NoticeDetails>> getNotices();
}
