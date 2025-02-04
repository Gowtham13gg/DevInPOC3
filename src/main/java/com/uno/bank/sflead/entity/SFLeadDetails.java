package com.uno.bank.sflead.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Map;

@Entity
@Table(name = "sf_lead_details")
@Data
public class SFLeadDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String applicationId;

    @Column(nullable = false)
    private String leadId;

    @Column(nullable = false)
    private String clientId;

    @Column(nullable = false, unique = true)
    private String mobileNo;

    private String lastScreen;
    private String applicationStatus;

    @ElementCollection
    @CollectionTable(name = "personal_details", joinColumns = @JoinColumn(name = "lead_id"))
    @MapKeyColumn(name = "detail_key")
    @Column(name = "detail_value")
    private Map<String, String> personalDetails;

    @ElementCollection
    @CollectionTable(name = "address_details", joinColumns = @JoinColumn(name = "lead_id"))
    @MapKeyColumn(name = "address_type")
    @Column(name = "address")
    private Map<String, String> addressDetails;

    @ElementCollection
    @CollectionTable(name = "occupation_details", joinColumns = @JoinColumn(name = "lead_id"))
    @MapKeyColumn(name = "detail_key")
    @Column(name = "detail_value")
    private Map<String, String> occupationDetails;

    @ElementCollection
    @CollectionTable(name = "consent_details", joinColumns = @JoinColumn(name = "lead_id"))
    @MapKeyColumn(name = "consent_type")
    @Column(name = "consent_value")
    private Map<String, String> consentDetails;
}
