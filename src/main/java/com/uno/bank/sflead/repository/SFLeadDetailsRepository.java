package com.uno.bank.sflead.repository;

import com.uno.bank.sflead.entity.SFLeadDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface SFLeadDetailsRepository extends JpaRepository<SFLeadDetails, Long> {
    Optional<SFLeadDetails> findByMobileNo(String mobileNo);
}
