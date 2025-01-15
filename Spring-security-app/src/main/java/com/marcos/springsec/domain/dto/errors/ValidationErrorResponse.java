package com.marcos.springsec.domain.dto.errors;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ValidationErrorResponse extends ErrorResponse {

    private Map<String, String> errors = new HashMap<>();

    public ValidationErrorResponse(String title, String detail, Map<String, String> errors) {
        super(title, detail);
        this.errors = errors;
    }
}
