package com.marcos.springsec.domain.dto.internal;

import com.marcos.springsec.domain.entity.Authorities;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private String mobileNumber;
    private String role;
    private String createDt;
    private List<AuthoritiesResponse> authorities;
}