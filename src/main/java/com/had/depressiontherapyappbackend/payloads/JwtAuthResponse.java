package com.had.depressiontherapyappbackend.payloads;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JwtAuthResponse {

    public JwtAuthResponse(String token) {
        this.token = token;
    }
    
    private String token;

}
