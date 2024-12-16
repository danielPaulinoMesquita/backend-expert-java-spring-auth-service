package br.com.daniel.authserviceapi.repositories;

import br.com.daniel.authserviceapi.models.RefreshToken;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends MongoRepository<RefreshToken, String> {
}
