package net.cookiespoll.utils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.Cookie;

import java.util.Optional;

import static org.mockito.Mockito.*;

public class TestCookieWebUtils {
    private HttpServletResponse response = mock(HttpServletResponse.class);
    private HttpServletRequest request = mock(HttpServletRequest.class);
    private Cookie cookie = new Cookie("cookie", "value");

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddCookie () {
       doNothing().when(response).addCookie(any(Cookie.class));

       CookieWebUtils.addCookie(response, "cookie", "value", 180);

       verify(response).addCookie(any(Cookie.class));
    }

    @Test
    public void testGetCookie () {
        Cookie[] cookies = {cookie, new Cookie("cookie2", "value2")};

        when(request.getCookies()).thenReturn(cookies);

        Optional<Cookie> resultCookie = CookieWebUtils.getCookie(request, "cookie");

        Assert.assertEquals(cookie.getName(), resultCookie.get().getName());
        Assert.assertEquals(cookie.getValue(), resultCookie.get().getValue());

        verify(request).getCookies();
    }

    @Test
    public void testDeleteCookie () {
        Cookie[] cookies = {cookie, new Cookie("cookie2", "value2")};

        when(request.getCookies()).thenReturn(cookies);
        doNothing().when(response).addCookie(any(Cookie.class));

        CookieWebUtils.deleteCookie(request, response, "cookie");

        verify(request).getCookies();
        verify(response).addCookie(any(Cookie.class));
    }
}
