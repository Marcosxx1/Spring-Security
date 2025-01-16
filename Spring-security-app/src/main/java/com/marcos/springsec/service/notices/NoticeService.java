package com.marcos.springsec.service.notices;

import com.marcos.springsec.domain.entity.NoticeDetails;

import java.util.List;

public interface NoticeService {

    List<NoticeDetails> findAllActiveNotices();
}
