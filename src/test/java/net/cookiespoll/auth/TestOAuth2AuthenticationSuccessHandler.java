package net.cookiespoll.auth;

import net.cookiespoll.utils.CookieWebUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.security.core.Authentication;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.when;

public class TestOAuth2AuthenticationSuccessHandler {
    private HttpServletResponse response = mock(HttpServletResponse.class);
    private HttpServletRequest request = mock(HttpServletRequest.class);
    private TokenProvider tokenProvider = mock(TokenProvider.class);
    private Authentication authentication = mock(Authentication.class);
    private HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository =
            mock(HttpCookieOAuth2AuthorizationRequestRepository.class);
    private Cookie cookieOAuth2 = new Cookie("oauth2_auth_request", "value");
    private OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private CookieWebUtils cookieWebUtils = mock(CookieWebUtils.class);

    public static final String REDIRECT_URI_PARAM_COOKIE_NAME = "redirect_uri";

    @Before
    public void init() {
        oAuth2AuthenticationSuccessHandler = new OAuth2AuthenticationSuccessHandler(tokenProvider,
                httpCookieOAuth2AuthorizationRequestRepository, cookieWebUtils);

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testOnAuthenticationSuccess () throws IOException {
        Cookie cookie = new Cookie("redirect_uri", "value");
        Cookie[] cookies = {cookie, new Cookie("cookie2", "value2")};

        when(request.getCookies()).thenReturn(cookies);
        when(tokenProvider.createToken(authentication)).thenReturn("token");
        doNothing().when(httpCookieOAuth2AuthorizationRequestRepository).removeAuthorizationRequestCookies(request, response);
        when(response.isCommitted()).thenReturn(false);

        oAuth2AuthenticationSuccessHandler.onAuthenticationSuccess(request, response, authentication);

        verify(request).getCookies();
        verify(tokenProvider).createToken(authentication);
        verify(httpCookieOAuth2AuthorizationRequestRepository).removeAuthorizationRequestCookies(request, response);
        verify(response).isCommitted();
    }
}
