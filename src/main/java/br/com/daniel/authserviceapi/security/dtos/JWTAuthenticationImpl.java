package br.com.daniel.authserviceapi.security.dtos;

import br.com.daniel.authserviceapi.utils.JWTUtils;
import br.com.userservice.commonslib.model.requests.AuthenticateRequest;
import br.com.userservice.commonslib.model.responses.AuthenticateResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Log4j2
@RequiredArgsConstructor
public class JWTAuthenticationImpl {

    private final JWTUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    public AuthenticateResponse authenticate(final AuthenticateRequest request) {
        log.info("Authenticating request: {}", request.email());
        try {
            final var authResult = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.email(), request.password()));
            return buildAuthenticateResponse((UserDetailsDTO) authResult.getPrincipal());
        } catch (BadCredentialsException e) {
            log.error("Error on authenticate user: {}", e.getMessage());
            throw new BadCredentialsException(e.getMessage());
        }
    }

    protected AuthenticateResponse buildAuthenticateResponse(final UserDetailsDTO detailsDTO) {
        log.info("Successfully authenticated response: {}", detailsDTO.getName());
        final var token = jwtUtils.generateToken(detailsDTO);
        return AuthenticateResponse.builder()
                .type("Bearer")
                .token(token)
                .build();
    }
}

