package com.clone.instagram.authSetvice.Payload.Response;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class JwtResponse {

    @NonNull
    private String token;
    private String type = "Bearer";
}

