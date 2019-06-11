package net.cookiespoll.auth;

import net.cookiespoll.utils.CookieWebUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(CookieWebUtils.class)
public class TestHttpCookieOAuth2AuthorizationRequestRepository {
    private HttpServletResponse response = mock(HttpServletResponse.class);
    private HttpServletRequest request = mock(HttpServletRequest.class);
    private CookieWebUtils cookieWebUtils = mock(CookieWebUtils.class);
    private HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository = new HttpCookieOAuth2AuthorizationRequestRepository();
    public static final String OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME = "oauth2_auth_request";
    private Cookie cookieOAuth2 = new Cookie("oauth2_auth_request", "value");

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testLoadAuthorizationRequest () {
        PowerMockito.mockStatic(CookieWebUtils.class);
        OAuth2AuthorizationRequest requestOAuth2 = OAuth2AuthorizationRequest.authorizationCode().authorizationUri("234").clientId("12").build();

        when(CookieWebUtils.getCookie(request, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME))
                .thenReturn(Optional.of(cookieOAuth2));
        when(CookieWebUtils.deserialize(cookieOAuth2, OAuth2AuthorizationRequest.class)).thenReturn(requestOAuth2);

        OAuth2AuthorizationRequest resultOAuth2 = httpCookieOAuth2AuthorizationRequestRepository.loadAuthorizationRequest(request);

        Assert.assertEquals(resultOAuth2, requestOAuth2);
    }
}
