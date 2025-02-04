package com.uno.bank.sflead.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uno.bank.sflead.dto.LeadDetailsRequest;
import com.uno.bank.sflead.entity.SFLeadDetails;
import com.uno.bank.sflead.exception.LeadDetailsException;
import com.uno.bank.sflead.service.SFLeadDetailsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.HashMap;
import java.util.Map;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SFLeadDetailsController.class)
class SFLeadDetailsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SFLeadDetailsService leadDetailsService;

    private LeadDetailsRequest createValidRequest() {
        LeadDetailsRequest request = new LeadDetailsRequest();
        request.setApplicationId("APP001");
        request.setLeadId("LEAD001");
        request.setClientId("CLIENT001");
        request.setMobileNo("1234567890");
        request.setLastScreen("PERSONAL_DETAILS");
        request.setApplicationStatus("IN_PROGRESS");
        return request;
    }

    @Test
    void testInsertLeadDetailsSuccess() throws Exception {
        LeadDetailsRequest request = createValidRequest();
        Map<String, String> personalDetails = new HashMap<>();
        personalDetails.put("age", "30");
        request.setPersonalDetails(personalDetails);

        mockMvc.perform(post("/api/app/insert/SFLead/details")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Lead details inserted successfully"));

        verify(leadDetailsService).insertLeadDetails(any(LeadDetailsRequest.class));
    }

    @Test
    void testInsertLeadDetailsInvalidAge() throws Exception {
        LeadDetailsRequest request = createValidRequest();
        Map<String, String> personalDetails = new HashMap<>();
        personalDetails.put("age", "invalid");
        request.setPersonalDetails(personalDetails);

        doThrow(new LeadDetailsException("Age must be numeric only, if provided"))
            .when(leadDetailsService).insertLeadDetails(any(LeadDetailsRequest.class));

        mockMvc.perform(post("/api/app/insert/SFLead/details")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Age must be numeric only, if provided"));
    }

    @Test
    void testMobileInquirySuccess() throws Exception {
        String mobileNo = "1234567890";
        SFLeadDetails mockLead = new SFLeadDetails();
        mockLead.setMobileNo(mobileNo);
        when(leadDetailsService.findByMobileNo(mobileNo)).thenReturn(mockLead);

        mockMvc.perform(get("/api/app/insert/SFLead/inquiry")
                .param("mobileNo", mobileNo))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mobileNo").value(mobileNo));
    }

    @Test
    void testMobileInquiryNotFound() throws Exception {
        String mobileNo = "9999999999";
        when(leadDetailsService.findByMobileNo(mobileNo))
            .thenThrow(new LeadDetailsException("No lead found for mobile number: " + mobileNo));

        mockMvc.perform(get("/api/app/insert/SFLead/inquiry")
                .param("mobileNo", mobileNo))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("No lead found for mobile number: " + mobileNo));
    }
}
