package net.cookiespoll.auth;

import net.cookiespoll.utils.CookieWebUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static net.cookiespoll.auth.HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;
import static org.mockito.Mockito.*;

public class TestOAuth2AuthenticationFailureHandler {
    private HttpServletResponse response = mock(HttpServletResponse.class);
    private HttpServletRequest request = mock(HttpServletRequest.class);
    private TokenProvider tokenProvider = mock(TokenProvider.class);
    private Authentication authentication = mock(Authentication.class);
    private HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository =
            mock(HttpCookieOAuth2AuthorizationRequestRepository.class);
    private AuthenticationException e = mock(AuthenticationException.class);
    private CookieWebUtils cookieWebUtils = mock(CookieWebUtils.class);
    private OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

    @Before
    public void init() {
        oAuth2AuthenticationFailureHandler = new OAuth2AuthenticationFailureHandler(httpCookieOAuth2AuthorizationRequestRepository, cookieWebUtils);

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testOnAuthenticationFailure () throws IOException {
        Cookie cookie = new Cookie("redirect_uri", "value");

        when(cookieWebUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)).thenReturn(Optional.of(cookie));
        doNothing().when(httpCookieOAuth2AuthorizationRequestRepository).removeAuthorizationRequestCookies(request, response);
        when(e.getLocalizedMessage()).thenReturn("Credential are wrong");

        oAuth2AuthenticationFailureHandler.onAuthenticationFailure(request, response, e);

        verify(cookieWebUtils).getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME);
        verify(httpCookieOAuth2AuthorizationRequestRepository).removeAuthorizationRequestCookies(request, response);
        verify(e).getLocalizedMessage();
    }
}
