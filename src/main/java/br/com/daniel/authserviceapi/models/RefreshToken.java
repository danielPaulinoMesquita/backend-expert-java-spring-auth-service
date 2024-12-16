package br.com.daniel.authserviceapi.models;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Builder
@Document
public class RefreshToken {

    @Id
    private String id;
    private String userName;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
}
