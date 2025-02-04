package com.uno.bank.sflead.model;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import lombok.Data;
import java.util.List;

@Data
@Embeddable
public class ApplicationDetails {
    
    @ElementCollection
    private List<Address> address;
    
    private String consent;
    private String kycDetails;
    private String occupation;
    private String personalDetails;
    private String additionalDetails;
}
