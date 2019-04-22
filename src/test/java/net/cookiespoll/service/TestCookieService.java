package net.cookiespoll.service;
import net.cookiespoll.daoimpl.CookieDaoImpl;
import net.cookiespoll.dto.AddCookieDtoRequest;
import net.cookiespoll.model.Cookie;
import net.cookiespoll.model.CookieAddingStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;

import java.io.*;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TestCookieService {
    @Mock
    CookieDaoImpl cookieDao;
    @InjectMocks
    CookieService cookieService;

    Cookie cookie = new Cookie("cookie", "tasty cookie", new byte[2], CookieAddingStatus.WAITING);
    Cookie cookieWithId = new Cookie(1, "cookie", "tasty cookie", new byte[2],CookieAddingStatus.WAITING,
            0);
    MockMultipartFile mockMultipartFile = new MockMultipartFile("testcookie.jpg", new byte[2]);
    AddCookieDtoRequest addCookieDtoRequest = new AddCookieDtoRequest("cookie", "tasty cookie");

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCookieDaoInsert() throws IOException {

        when(cookieDao.insert(cookie)).thenReturn(cookieWithId);
        Cookie resultCookie = cookieService.addCookie(addCookieDtoRequest, mockMultipartFile);

        Assert.assertEquals(1, resultCookie.getId());
        Assert.assertEquals(addCookieDtoRequest.getName(), resultCookie.getName());
        Assert.assertEquals(addCookieDtoRequest.getDescription(), resultCookie.getDescription());
        Assert.assertArrayEquals(mockMultipartFile.getBytes(), resultCookie.getFileData());
        Assert.assertEquals(cookie.getCookieAddingStatus(), resultCookie.getCookieAddingStatus());
        Assert.assertEquals(0, resultCookie.getRating());

        verify(cookieDao).insert(cookie);

    }

}
