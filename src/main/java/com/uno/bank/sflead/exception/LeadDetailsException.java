package com.uno.bank.sflead.exception;

public class LeadDetailsException extends RuntimeException {
    public LeadDetailsException(String message) {
        super(message);
    }

    public LeadDetailsException(String message, Throwable cause) {
        super(message, cause);
    }
}
