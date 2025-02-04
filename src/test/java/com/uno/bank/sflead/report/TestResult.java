package com.uno.bank.sflead.report;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class TestResult {
    private String testClass;
    private String testMethod;
    private String status;
    private String errorMessage;
    private long duration;
}
