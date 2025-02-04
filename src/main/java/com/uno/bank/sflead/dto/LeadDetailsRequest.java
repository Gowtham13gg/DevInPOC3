package com.uno.bank.sflead.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import java.util.Map;

@Data
public class LeadDetailsRequest {
    @NotBlank(message = "Application ID is required")
    private String applicationId;

    @NotBlank(message = "Lead ID is required")
    private String leadId;

    @NotBlank(message = "Client ID is required")
    private String clientId;

    @NotBlank(message = "Mobile number is required")
    @Pattern(regexp = "\\d{10}", message = "Mobile number must be 10 digits")
    private String mobileNo;

    private String lastScreen;
    private String applicationStatus;
    private Map<String, String> personalDetails;
    private Map<String, String> occupationDetails;
    private Map<String, String> consentDetails;
}
