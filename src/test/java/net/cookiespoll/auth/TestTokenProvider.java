package net.cookiespoll.auth;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;

import java.time.Instant;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.*;

public class TestTokenProvider {
    private TokenProvider tokenProvider = new TokenProvider();
    private Authentication authentication = mock(Authentication.class);

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
    public void testGetUserFromToken () {
        String token = "12345";

        Optional<Map<String, String>> stringMap = tokenProvider.getUserFromToken(token);

        Assert.assertEquals(stringMap, Optional.empty());
    }
}
