package com.marcos.springsec.domain.dto.internal;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
// TODO Adicionar anotações do swagger
public class CustomerRegistrationRequest {

    @NotEmpty(message = "name cannot be empty")
    private String name;

    @NotEmpty(message = "email cannot be empty")
    private String email;

    @NotEmpty(message = "mobile number cannot be empty")
    private String mobileNumber;

    @NotEmpty(message = "password cannot be empty")
    private String password;

    @NotEmpty(message = "role cannot be empty")
    private String role;
}
