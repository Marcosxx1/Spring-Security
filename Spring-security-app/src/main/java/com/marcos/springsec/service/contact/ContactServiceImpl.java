package com.marcos.springsec.service.contact;

import com.marcos.springsec.domain.entity.ContactMessages;
import com.marcos.springsec.repository.contact.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;

    @Override
    public ContactMessages saveContactInquiryDetails(ContactMessages contactMessages) {
        return contactRepository.save(contactMessages);
    }
}
