package com.marcos.springsec.repository.notice;

import com.marcos.springsec.domain.entity.NoticeDetails;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Repository
public interface NoticeRepository extends CrudRepository<NoticeDetails, Long> {

    Optional<List<NoticeDetails>> findByNoticBegDtLessThanEqualAndNoticEndDtGreaterThanEqual(LocalDate date);
}
