package com.marcos.springsec.domain.dto.internal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthoritiesResponse {
    private String name;
}