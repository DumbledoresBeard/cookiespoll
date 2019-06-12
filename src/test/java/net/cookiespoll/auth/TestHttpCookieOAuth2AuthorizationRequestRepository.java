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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(CookieWebUtils.class)
public class TestHttpCookieOAuth2AuthorizationRequestRepository {
    private HttpServletResponse response = mock(HttpServletResponse.class);
    private HttpServletRequest request = mock(HttpServletRequest.class);
    private CookieWebUtils cookieWebUtils = mock(CookieWebUtils.class);
    private HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;
    private Cookie cookieOAuth2 = new Cookie("oauth2_auth_request", "value");
    private OAuth2AuthorizationRequest requestOAuth2 = OAuth2AuthorizationRequest.authorizationCode().authorizationUri("234").clientId("12").build();

    public static final String OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME = "oauth2_auth_request";
    public static final String REDIRECT_URI_PARAM_COOKIE_NAME = "redirect_uri";
    private static final int cookieExpireSeconds = 180;

    @Before
    public void init() {
        httpCookieOAuth2AuthorizationRequestRepository = new HttpCookieOAuth2AuthorizationRequestRepository(cookieWebUtils);

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testLoadAuthorizationRequest () {
        when(cookieWebUtils.getCookie(request, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME))
                .thenReturn(Optional.of(cookieOAuth2));
        when(cookieWebUtils.deserialize(cookieOAuth2, OAuth2AuthorizationRequest.class)).thenReturn(requestOAuth2);

        OAuth2AuthorizationRequest resultOAuth2 = httpCookieOAuth2AuthorizationRequestRepository.loadAuthorizationRequest(request);

        Assert.assertEquals(resultOAuth2, requestOAuth2);
    }

    @Test
    public void testSaveAuthorizationRequestOAuth2AuthorizationRequestNotNull () throws Exception {
//        PowerMockito.mockStatic(CookieWebUtils.class);
//
//        when(CookieWebUtils.serialize(requestOAuth2)).thenReturn("serialized request");
//        doNothing().when(CookieWebUtils.class, "addCookie", response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME,
//                CookieWebUtils.serialize(requestOAuth2), cookieExpireSeconds);
        when(request.getParameter(REDIRECT_URI_PARAM_COOKIE_NAME)).thenReturn("redirect_uri_after_login");
//        doNothing().when(CookieWebUtils.class, "addCookie", response, REDIRECT_URI_PARAM_COOKIE_NAME, "redirect_uri_after_login",
//                cookieExpireSeconds);

        httpCookieOAuth2AuthorizationRequestRepository.saveAuthorizationRequest(requestOAuth2, request, response);

//        verify(response).addCookie(any(Cookie.class));
//        verify(request).getParameter(REDIRECT_URI_PARAM_COOKIE_NAME);
//        PowerMockito.verifyStatic(CookieWebUtils);
    }
}
