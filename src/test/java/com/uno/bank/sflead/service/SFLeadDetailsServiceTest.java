package com.uno.bank.sflead.service;

import com.uno.bank.sflead.dto.LeadDetailsRequest;
import com.uno.bank.sflead.entity.SFLeadDetails;
import com.uno.bank.sflead.exception.LeadDetailsException;
import com.uno.bank.sflead.repository.SFLeadDetailsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SFLeadDetailsServiceTest {

    @Mock
    private SFLeadDetailsRepository leadDetailsRepository;

    @InjectMocks
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
    void testValidAge() {
        LeadDetailsRequest request = createValidRequest();
        Map<String, String> personalDetails = new HashMap<>();
        personalDetails.put("age", "30");
        request.setPersonalDetails(personalDetails);

        assertDoesNotThrow(() -> leadDetailsService.insertLeadDetails(request));
        verify(leadDetailsRepository).save(any(SFLeadDetails.class));
    }

    @Test
    void testInvalidAgeFormat() {
        LeadDetailsRequest request = createValidRequest();
        Map<String, String> personalDetails = new HashMap<>();
        personalDetails.put("age", "abc");
        request.setPersonalDetails(personalDetails);

        LeadDetailsException exception = assertThrows(
            LeadDetailsException.class,
            () -> leadDetailsService.insertLeadDetails(request)
        );
        assertEquals("Age must be numeric only, if provided", exception.getMessage());
    }

    @Test
    void testAgeOutOfRange() {
        LeadDetailsRequest request = createValidRequest();
        Map<String, String> personalDetails = new HashMap<>();
        personalDetails.put("age", "121");
        request.setPersonalDetails(personalDetails);

        LeadDetailsException exception = assertThrows(
            LeadDetailsException.class,
            () -> leadDetailsService.insertLeadDetails(request)
        );
        assertEquals("Age must be between 0 and 120, if provided", exception.getMessage());
    }

    @Test
    void testMobileInquiryFound() {
        String mobileNo = "1234567890";
        SFLeadDetails mockLead = new SFLeadDetails();
        mockLead.setMobileNo(mobileNo);
        when(leadDetailsRepository.findByMobileNo(mobileNo)).thenReturn(Optional.of(mockLead));

        SFLeadDetails result = leadDetailsService.findByMobileNo(mobileNo);
        assertNotNull(result);
        assertEquals(mobileNo, result.getMobileNo());
    }

    @Test
    void testMobileInquiryNotFound() {
        String mobileNo = "9999999999";
        when(leadDetailsRepository.findByMobileNo(mobileNo)).thenReturn(Optional.empty());

        LeadDetailsException exception = assertThrows(
            LeadDetailsException.class,
            () -> leadDetailsService.findByMobileNo(mobileNo)
        );
        assertEquals("No lead found for mobile number: " + mobileNo, exception.getMessage());
    }
}
