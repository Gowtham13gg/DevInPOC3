package com.uno.bank.sflead.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class JsonUtil {
    private final ObjectMapper objectMapper;
    
    public String mapToJson(Map<String, String> map) {
        try {
            return map != null ? objectMapper.writeValueAsString(map) : null;
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error converting map to JSON: " + e.getMessage());
        }
    }
}
