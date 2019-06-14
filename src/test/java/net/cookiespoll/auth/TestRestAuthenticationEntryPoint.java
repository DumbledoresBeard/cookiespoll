package net.cookiespoll.auth;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class TestRestAuthenticationEntryPoint {
    private HttpServletResponse response = mock(HttpServletResponse.class);
    private HttpServletRequest request = mock(HttpServletRequest.class);
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint = new RestAuthenticationEntryPoint();
    private AuthenticationException e = new CredentialsExpiredException("Credentials are expired");

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCommence () throws IOException, ServletException {
        doNothing().when(response).sendError(401, e.getLocalizedMessage());

        restAuthenticationEntryPoint.commence(request, response, e);

        verify(response).sendError(401, e.getLocalizedMessage());
    }
}
