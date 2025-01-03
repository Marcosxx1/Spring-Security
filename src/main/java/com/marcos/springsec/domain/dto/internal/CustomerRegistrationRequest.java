package com.marcos.springsec.domain.dto.internal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
// TODO Adicionar anotações do swagger
public class CustomerRegistrationRequest {

    private String email;

    private String password;

    private String role;
}
