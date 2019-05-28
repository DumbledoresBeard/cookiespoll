package net.cookiespoll.controller;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.time.Instant;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {

        @Override
        public SecurityContext createSecurityContext(WithMockCustomUser customUser) {
            SecurityContext context = SecurityContextHolder.createEmptyContext();

            Map<String, Object> claims = Map.of("sub", "12345", "name", "name",
                    "email", "a@lineate.com");
            OidcUserInfo oidcUserInfo = new OidcUserInfo(claims);

            Set<GrantedAuthority> authorities = new HashSet<>();
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("user");
            authorities.add(grantedAuthority);

            Instant issuedAt = Instant.now();
            Instant expiresAt = Instant.now();
            OidcIdToken oidcIdToken = new OidcIdToken("token", issuedAt, expiresAt, Map.of("sub", "12345", "name", "name",
                    "email", "a@lineate.com"));

            DefaultOidcUser defaultOidcUser = new DefaultOidcUser(authorities, oidcIdToken, oidcUserInfo);

            Authentication auth =
                    new UsernamePasswordAuthenticationToken(defaultOidcUser, "password", defaultOidcUser.getAuthorities());
            context.setAuthentication(auth);
            return context;
        }
}
