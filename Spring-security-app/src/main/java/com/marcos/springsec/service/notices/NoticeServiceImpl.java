package com.marcos.springsec.service.notices;

import com.marcos.springsec.domain.entity.NoticeDetails;
import com.marcos.springsec.repository.notice.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService{

    private final NoticeRepository noticeRepository;

    @Override
    public List<NoticeDetails> findAllActiveNotices() {
        return noticeRepository.findByNoticBegDtLessThanEqualAndNoticEndDtGreaterThanEqual(LocalDate.now()).orElse(null);
    }

}
