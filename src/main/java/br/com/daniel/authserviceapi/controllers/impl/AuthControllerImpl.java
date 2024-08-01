package br.com.daniel.authserviceapi.controllers.impl;

import br.com.daniel.authserviceapi.controllers.AuthController;
import br.com.userservice.commonslib.model.requests.AuthenticateRequest;
import br.com.userservice.commonslib.model.responses.AuthenticateResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthControllerImpl implements AuthController {

    @Override
    public ResponseEntity<AuthenticateResponse> authenticate(AuthenticateRequest authenticateRequest) {
        return ResponseEntity.ok().body(AuthenticateResponse
                .builder()
                .type("Bearer")
                .token("token")
                .build());
    }
}
