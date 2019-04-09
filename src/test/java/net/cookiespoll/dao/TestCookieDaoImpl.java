package net.cookiespoll.dao;

import net.cookiespoll.daoimpl.CookieDaoImpl;
import net.cookiespoll.mapper.CookieMapper;
import net.cookiespoll.model.Cookie;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TestCookieDaoImpl {

    @Mock
    CookieMapper cookieMapper;
    @InjectMocks
    CookieDaoImpl cookieDaoImpl;

    Cookie cookie = new Cookie("cookie", "tasty cookie", new byte[2]);
    Cookie returnCookie = new Cookie("cookie", "tasty cookie", new byte[2]);

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCookieDaoInsert() {
        when(cookieDaoImpl.insert(cookie)).thenReturn(cookie);

        Assert.assertEquals (cookie, returnCookie);
        verify(cookieDaoImpl).insert(cookie);

    }



}
