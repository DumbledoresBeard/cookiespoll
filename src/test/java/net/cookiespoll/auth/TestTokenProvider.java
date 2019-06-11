package net.cookiespoll.auth;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.Instant;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.*;

public class TestTokenProvider {
    private TokenProvider tokenProvider;
    private Authentication authentication = mock(Authentication.class);
    private RestTemplate restTemplate = mock(RestTemplate.class);
    ResponseEntity response = mock(ResponseEntity.class);
    public static final String GOOGLE_API = "https://www.googleapis.com/oauth2/v3/tokeninfo?id_token=";

    @Before
    public void init() {
        tokenProvider = new TokenProvider(restTemplate);

        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testCreateToken () {
        Instant issuedAt = Instant.now();
        Instant expiresAt = Instant.now().plusSeconds(10000);
        OidcIdToken oidcIdToken = new OidcIdToken("token", issuedAt, expiresAt, Map.of("sub", "12345", "name", "name",
                "email", "a@mail.com"));
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("user"));

        DefaultOidcUser defaultOidcUser = new DefaultOidcUser(authorities, oidcIdToken);
        Object object = defaultOidcUser;

        when(authentication.getPrincipal()).thenReturn(object);

        String token = tokenProvider.createToken(authentication);

        Assert.assertEquals(oidcIdToken.getTokenValue(), token);

        verify(authentication).getPrincipal();
    }

    @Test
    public void testGetUserFromToken () throws IOException {
        String token = "12345";
        Map<String, String> userData = (Map.of("sub", "12345"));

        when(restTemplate.getForObject(GOOGLE_API + token.trim(), Map.class)).thenReturn(userData);

        Optional<Map<String, String>> resultUserData = tokenProvider.getUserFromToken(token);

        Assert.assertEquals(resultUserData.get(), userData);
    }


    @Test
    public void testGetUserFromTokenInvalidToken () throws IOException {
        String token = "12345";
        Map<String, String> userData = null;

        when(restTemplate.getForObject(GOOGLE_API + token.trim(), Map.class)).thenReturn(userData);

        Optional<Map<String, String>> resultUserData = tokenProvider.getUserFromToken(token);

        Assert.assertEquals(resultUserData, Optional.empty());
    }
}
