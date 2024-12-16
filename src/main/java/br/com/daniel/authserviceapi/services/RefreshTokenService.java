package br.com.daniel.authserviceapi.services;

import br.com.daniel.authserviceapi.models.RefreshToken;
import br.com.daniel.authserviceapi.repositories.RefreshTokenRepository;
import br.com.daniel.authserviceapi.security.dtos.UserDetailsDTO;
import br.com.daniel.authserviceapi.utils.JWTUtils;
import br.com.userservice.commonslib.model.exceptions.RefreshTokenExpired;
import br.com.userservice.commonslib.model.exceptions.ResourceNotFoundException;
import br.com.userservice.commonslib.model.responses.RefreshTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    @Value("${jwt.expiration-sec.refresh-token}")
    private Long refreshTokenExpirationSec;

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserDetailsService userDetailsService;
    private final JWTUtils jwtUtils;

    public RefreshToken save(final String userName) {
        return refreshTokenRepository.save(RefreshToken.builder()
                .id(UUID.randomUUID().toString())
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusSeconds(refreshTokenExpirationSec))
                .userName(userName)
                .build()
        );
    }

    public RefreshTokenResponse refreshToken(final String refreshTokenId) {
        final var refreshToken = refreshTokenRepository.findById(refreshTokenId)
                .orElseThrow(() -> new ResourceNotFoundException("Refresh token not found. ID: " + refreshTokenId));

        if (refreshToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new RefreshTokenExpired("Refresh token expired. ID: " + refreshTokenId);
        }

        return new RefreshTokenResponse(jwtUtils.generateToken((UserDetailsDTO)
                userDetailsService.loadUserByUsername(refreshToken.getUserName())));
    }
}
