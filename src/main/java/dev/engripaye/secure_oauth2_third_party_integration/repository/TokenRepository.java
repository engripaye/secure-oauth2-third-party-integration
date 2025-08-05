package dev.engripaye.secure_oauth2_third_party_integration.repository;

import dev.engripaye.secure_oauth2_third_party_integration.model.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<TokenEntity, String> {
    TokenEntity findByUserIdAndProvider(String userId, String provider);
}
