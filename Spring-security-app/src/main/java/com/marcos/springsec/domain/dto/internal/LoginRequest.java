package com.marcos.springsec.domain.dto.internal;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor // Add this
@AllArgsConstructor
// TODO Adicionar anotações do swagger
public class LoginRequest {
    @NotEmpty(message = "email cannot be empty")
    private String email;

    @NotEmpty(message = "password cannot be empty")
    private String password;

}
