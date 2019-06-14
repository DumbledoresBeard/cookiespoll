package net.cookiespoll.controller;

import net.cookiespoll.model.Cookie;
import net.cookiespoll.model.CookieAddingStatus;
import net.cookiespoll.model.CookieUserRating;
import net.cookiespoll.model.user.Role;
import net.cookiespoll.model.user.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {

        @Override
        public SecurityContext createSecurityContext(WithMockCustomUser customUser) {
            SecurityContext context = SecurityContextHolder.createEmptyContext();

            Set<GrantedAuthority> authorities = new HashSet<>();
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("user");
            authorities.add(grantedAuthority);

            User user = new User("1", "login", "name", Role.USER);
            Cookie cookieWith1Id = new Cookie(1, "cookie", "tasty cookie",
                    new byte[2], CookieAddingStatus.APPROVED, (float) 0, user);
            List<CookieUserRating> ratedByUser = new ArrayList<>();
            ratedByUser.add(new CookieUserRating(user, cookieWith1Id, 3));
            user.setRatedCookies(ratedByUser);
            List<Cookie> addedCookies = new ArrayList<>();
            addedCookies.add(cookieWith1Id);
            user.setAddedCookies(addedCookies);

            Authentication auth = new UsernamePasswordAuthenticationToken(user, "password", authorities);
            context.setAuthentication(auth);
            return context;
        }
}
