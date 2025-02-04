package com.uno.bank.sflead.repository;

import com.uno.bank.sflead.model.SFLeadDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SFLeadDetailsRepository extends JpaRepository<SFLeadDetails, String> {
    boolean existsByMobileNo(String mobileNo);
}
