package net.cookiespoll.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Service
public class TokenProvider {
    public static final String GOOGLE_API_TOKEN_URL = "https://www.googleapis.com/oauth2/v3/tokeninfo?id_token=";

    private RestTemplate restTemplate;

    @Autowired
    public TokenProvider(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String createToken(Authentication authentication) {
        DefaultOidcUser defaultOidcUser = (DefaultOidcUser) authentication.getPrincipal();

        return defaultOidcUser.getIdToken().getTokenValue();
    }

    public Optional <Map<String, String>> getUserFromToken(String token) throws IOException {
        Map<String, String> response = restTemplate.getForObject(GOOGLE_API_TOKEN_URL + token.trim(), Map.class);

        return Optional.ofNullable(response);
    }
}
