package net.cookiespoll.controller;

import net.cookiespoll.model.user.Role;
import net.cookiespoll.model.user.User;
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

            Set<GrantedAuthority> authorities = new HashSet<>();
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("user");
            authorities.add(grantedAuthority);

            User user = new User("12345", "a@lineate.com", "name", Role.USER);

            Authentication auth = new UsernamePasswordAuthenticationToken(user, "password", authorities);
            context.setAuthentication(auth);
            return context;
        }
}
