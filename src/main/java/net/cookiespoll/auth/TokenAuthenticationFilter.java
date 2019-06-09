package net.cookiespoll.auth;

import net.cookiespoll.model.user.User;
import net.cookiespoll.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private TokenProvider tokenProvider;
    private UserService userService;

    public static final String HEADER = "Authorization";
    public static final String STARTS_WITH = "Bearer ";
    public static final String TOKEN_KEY = "sub";

    @Autowired
    public TokenAuthenticationFilter(TokenProvider tokenProvider, UserService userService) {
        this.tokenProvider = tokenProvider;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain)
            throws ServletException, IOException {
        String token = getTokenFromRequest(httpServletRequest);
        Optional<Map<String, String>> googleData = tokenProvider.getUserFromToken(token);

        if (StringUtils.hasText(token) && googleData.isPresent()) {
            User user = userService.getById(googleData.get().get(TOKEN_KEY));

            List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(user.getRole().toString()));

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, authorities);
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(HEADER);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(STARTS_WITH)) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }
}
