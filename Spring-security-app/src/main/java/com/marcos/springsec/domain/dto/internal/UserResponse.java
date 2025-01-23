package com.marcos.springsec.domain.dto.internal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {

    private Long id;
    private String name;
    private String email;
    private String mobileNumber;
    private String role;
    private String createDt;
}