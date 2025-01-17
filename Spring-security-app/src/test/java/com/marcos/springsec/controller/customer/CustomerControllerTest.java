package com.marcos.springsec.controller.customer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcos.springsec.domain.dto.internal.CustomerRegistrationRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.marcos.springsec.constants.PathConstants.CUSTOMER;
import static com.marcos.springsec.constants.PathConstants.REGISTER;
import static org.hamcrest.Matchers.hasKey;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void testRegisterCustomer_whenRequiredFieldsMissing_throwValiationError() throws Exception{
        CustomerRegistrationRequest request = CustomerRegistrationRequest.builder().build();
        var requestJson = objectMapper.writeValueAsString(request);

        String[] invalidParams = {"name","email", "mobileNumber","password", "role"};

        var resultActions = mockMvc.perform(post(REGISTER).content(requestJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        for(String invalidParam : invalidParams){
             resultActions.andExpect(jsonPath("$.errors", hasKey(invalidParam)));
        }
    }
}
