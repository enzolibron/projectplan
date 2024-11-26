package com.llibron.projectplan.controllers;

import com.llibron.projectplan.dtos.authentication.AuthenticationRequest;
import com.llibron.projectplan.dtos.authentication.AuthenticationResponse;
import com.llibron.projectplan.services.JwtService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JwtController {

    private final JwtService jwtService;

    public JwtController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping("/authenticate")
    public AuthenticationResponse createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        return jwtService.createAuthenticationToken(authenticationRequest);
    }

    @PostMapping("/refresh-token")
    public AuthenticationResponse refreshToken(@RequestBody String refreshToken) {
        return jwtService.refreshToken(refreshToken);
    }
}
