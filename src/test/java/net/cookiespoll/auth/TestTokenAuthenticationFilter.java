package net.cookiespoll.auth;

import net.cookiespoll.model.user.Role;
import net.cookiespoll.model.user.User;
import net.cookiespoll.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TestTokenAuthenticationFilter {
    private HttpServletResponse response = mock(HttpServletResponse.class);
    private HttpServletRequest request = mock(HttpServletRequest.class);
    private TokenProvider tokenProvider = mock(TokenProvider.class);
    private UserService userService = mock(UserService.class);
    private FilterChain filterChain = mock(FilterChain.class);
    private TokenAuthenticationFilter tokenAuthenticationFilter;
    public static final String HEADER = "Authorization";

    @Before
    public void init() {
        tokenAuthenticationFilter = new TokenAuthenticationFilter(tokenProvider, userService);

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testDoFilterInternal () throws IOException, ServletException {
        Optional<Map<String, String>> userData = Optional.of(Map.of("sub", "12345"));
        User user = new User("12345", "login", "name",  Role.USER);
        String token = "Bearer 12345";

        when(request.getHeader(HEADER)).thenReturn(token);
        when(tokenProvider.getUserFromToken(any(String.class))).thenReturn(userData);
        when(userService.getById(any(String.class))).thenReturn(user);
        doNothing().when(filterChain).doFilter(request, response);

        tokenAuthenticationFilter.doFilterInternal(request, response, filterChain);

        User resultUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        verify(request).getHeader(HEADER);
        verify(tokenProvider).getUserFromToken(any(String.class));
        verify(userService).getById(any(String.class));
        verify(filterChain).doFilter(request, response);
    }
}
