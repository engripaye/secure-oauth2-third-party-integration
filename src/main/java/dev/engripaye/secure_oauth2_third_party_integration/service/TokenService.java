package dev.engripaye.secure_oauth2_third_party_integration.service;

import dev.engripaye.secure_oauth2_third_party_integration.model.TokenEntity;
import dev.engripaye.secure_oauth2_third_party_integration.repository.TokenRepository;
import dev.engripaye.secure_oauth2_third_party_integration.util.EncryptionUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    private final TokenRepository tokenRepository;
    private final EncryptionUtil encryptionUtil;


    public TokenService(TokenRepository tokenRepository, @Value("${app.encryption.secret-key}") String secretKey) {
        this.tokenRepository = tokenRepository;
        this.encryptionUtil = new EncryptionUtil(secretKey);
    }

    public void saveToken(String userId, String provider, String accessToken, String refreshToken, Long expiresIn, String scope) throws Exception {
        TokenEntity token = new TokenEntity();
        token.setUserId(userId);
        token.setProvider(provider);
        token.setAccessToken(encryptionUtil.encrypt(accessToken));
        if(refreshToken != null) {
            token.setRefreshToken(encryptionUtil.encrypt(refreshToken));
        }

        token.setExpiresIn(expiresIn);
        token.setScope(scope);
        tokenRepository.save(token);
    }

    public TokenEntity getToken(String userId, String provider){
        return tokenRepository.findByUserIdAndProvider(userId, provider);
    }

    public String decryptToken(String encryptedToken) throws Exception{
        return encryptionUtil.decrypt(encryptedToken);
    }
}
