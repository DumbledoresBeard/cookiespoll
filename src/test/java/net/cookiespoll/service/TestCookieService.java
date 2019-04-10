package net.cookiespoll.service;
import net.cookiespoll.daoimpl.CookieDaoImpl;
import net.cookiespoll.dto.AddCookieDtoRequest;
import net.cookiespoll.mapper.CookieMapper;
import net.cookiespoll.model.Cookie;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileItemHeaders;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
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

    Cookie cookie = new Cookie("cookie", "tasty cookie", new byte[2]);
    MockMultipartFile mockMultipartFile = new MockMultipartFile("testcookie.txt", new byte[2]);
    AddCookieDtoRequest addCookieDtoRequest = new AddCookieDtoRequest("cookie", "tasty cookie", mockMultipartFile);

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCookieDaoInsert() throws IOException {
        when(cookieDao.insert(cookie)).thenReturn(cookie);
        Cookie resultCookie = cookieService.addCookie(addCookieDtoRequest);
        verify(cookieDao).insert(cookie);

    }

}
