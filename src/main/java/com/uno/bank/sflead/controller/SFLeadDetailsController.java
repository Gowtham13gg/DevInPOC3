package com.uno.bank.sflead.controller;

import com.uno.bank.sflead.dto.LeadDetailsRequest;
import com.uno.bank.sflead.dto.ApiResponse;
import com.uno.bank.sflead.entity.SFLeadDetails;
import com.uno.bank.sflead.service.SFLeadDetailsService;
import com.uno.bank.sflead.exception.LeadDetailsException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/app/insert/SFLead")
public class SFLeadDetailsController {

    @Autowired
    private SFLeadDetailsService sfLeadDetailsService;

    @PostMapping("/details")
    public ResponseEntity<ApiResponse> insertLeadDetails(@Valid @RequestBody LeadDetailsRequest request) {
        sfLeadDetailsService.insertLeadDetails(request);
        return ResponseEntity.ok(new ApiResponse("Lead details inserted successfully"));
    }

    @GetMapping("/inquiry")
    public ResponseEntity<?> findByMobile(@RequestParam String mobileNo) {
        try {
            SFLeadDetails result = sfLeadDetailsService.findByMobileNo(mobileNo);
            return ResponseEntity.ok(result);
        } catch (LeadDetailsException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse(e.getMessage()));
        }
    }
}
