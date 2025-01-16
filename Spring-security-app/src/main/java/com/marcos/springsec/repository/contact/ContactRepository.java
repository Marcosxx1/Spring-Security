package com.marcos.springsec.repository.contact;

import com.marcos.springsec.domain.entity.ContactMessages;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends CrudRepository<ContactMessages, Long>{
}
