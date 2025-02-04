package com.uno.bank.sflead.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "SF_Lead_details")
public class SFLeadDetails {
    
    @Id
    @NotBlank(message = "Application ID is required")
    private String applicationId;
    
    @NotBlank(message = "Lead ID is required")
    private String leadId;
    
    @NotBlank(message = "Client ID is required")
    private String clientId;
    
    @NotBlank(message = "Mobile number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must be 10 digits")
    @Column(unique = true)
    private String mobileNo;
    
    private String lastScreen;
    private String applicationStatus;
    
    @Embedded
    private ApplicationDetails applicationDetails;
    
    @Column(updatable = false)
    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
