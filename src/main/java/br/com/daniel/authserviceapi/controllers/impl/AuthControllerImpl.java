package br.com.daniel.authserviceapi.controllers.impl;

import br.com.daniel.authserviceapi.controllers.AuthController;
import br.com.daniel.authserviceapi.security.dtos.JWTAuthenticationImpl;
import br.com.daniel.authserviceapi.utils.JWTUtils;
import br.com.userservice.commonslib.model.requests.AuthenticateRequest;
import br.com.userservice.commonslib.model.responses.AuthenticateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {

    private final JWTUtils jwtUtils;
    private final AuthenticationConfiguration authenticationConfiguration;

    @Override
    public ResponseEntity<AuthenticateResponse> authenticate(AuthenticateRequest authenticateRequest) throws Exception {
        return ResponseEntity.ok().body(
                new JWTAuthenticationImpl(jwtUtils, authenticationConfiguration.getAuthenticationManager())
                .authenticate(authenticateRequest));
    }
}
