package net.cookiespoll.service;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import net.cookiespoll.daoimpl.CookieDaoImpl;
import net.cookiespoll.dto.AddCookieRequest;
import net.cookiespoll.dto.UpdateCookieRequest;
import net.cookiespoll.model.Cookie;
import net.cookiespoll.model.CookieAddingStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TestCookieService {
    @Mock
    CookieDaoImpl cookieDao;
    @InjectMocks
    CookieService cookieService;

    private Cookie cookie = new Cookie("cookie", "tasty cookie", new byte[2],
            CookieAddingStatus.WAITING, (float) 0, 1);
    private MockMultipartFile mockMultipartFile = new MockMultipartFile("testcookie.jpg", new byte[2]);
    private Cookie cookieWithId = new Cookie(1, "cookie", "tasty cookie",
            new byte[2], CookieAddingStatus.WAITING, (float) 0, 1);
    private AddCookieRequest addCookieRequest = new AddCookieRequest("cookie",
            "tasty cookie");
    private Cookie cookieWith2Id = new Cookie(2,"cookie", "tasty cookie",
            new byte[2], CookieAddingStatus.WAITING, (float) 0, 2);
    private Integer userId = 1;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCookieDaoInsert() throws IOException {

        when(cookieDao.insert(cookie)).thenReturn(cookieWithId);
        Cookie resultCookie = cookieService.insert(addCookieRequest, mockMultipartFile, userId);

        Assert.assertEquals(1, resultCookie.getId());
        Assert.assertEquals(addCookieRequest.getName(), resultCookie.getName());
        Assert.assertEquals(addCookieRequest.getDescription(), resultCookie.getDescription());
        Assert.assertArrayEquals(mockMultipartFile.getBytes(), resultCookie.getFileData());
        Assert.assertEquals(cookie.getCookieAddingStatus(), resultCookie.getCookieAddingStatus());
        Assert.assertEquals(0, resultCookie.getRating(), 0.0f);
        Assert.assertEquals(1, resultCookie.getUserId());

        verify(cookieDao).insert(cookie);

    }

    @Test
    public void testGetCookiesByParam () {
        String name = "name";
        String description = "description";
        CookieAddingStatus cookieAddingStatus = CookieAddingStatus.WAITING;
        float rating = 0;
        List<Cookie> cookies = new ArrayList<>();
        cookies.add(cookieWithId);
        cookies.add(cookieWith2Id);

        when(cookieDao.getByParam(name, description, cookieAddingStatus, rating, userId))
                .thenReturn(cookies);

        List<Cookie> resultCookieList = cookieService.getByParam(name, description,
                cookieAddingStatus, rating, userId);

        Assert.assertEquals(resultCookieList.get(0).getId(), cookieWithId.getId());
        Assert.assertEquals(resultCookieList.get(0).getName(), cookieWithId.getName());
        Assert.assertEquals(resultCookieList.get(0).getDescription(), cookieWithId.getDescription());
        Assert.assertArrayEquals(resultCookieList.get(0).getFileData(), cookieWithId.getFileData());
        Assert.assertEquals(resultCookieList.get(0).getCookieAddingStatus(),
                            cookieWithId.getCookieAddingStatus());
        Assert.assertEquals(resultCookieList.get(0).getRating(), cookieWithId.getRating());

        Assert.assertEquals(resultCookieList.get(1).getId(), cookieWith2Id.getId());
        Assert.assertEquals(resultCookieList.get(1).getName(), cookieWith2Id.getName());
        Assert.assertEquals(resultCookieList.get(1).getDescription(), cookieWith2Id.getDescription());
        Assert.assertArrayEquals(resultCookieList.get(1).getFileData(), cookieWith2Id.getFileData());
        Assert.assertEquals(resultCookieList.get(1).getCookieAddingStatus(),
                                    cookieWith2Id.getCookieAddingStatus());
        Assert.assertEquals(resultCookieList.get(1).getRating(), cookieWith2Id.getRating());

        verify(cookieDao).getByParam(name, description, cookieAddingStatus, rating, userId);

    }

    @Test
    public void testUpdateCookie () {
        Cookie cookie = new Cookie(
                1, "cookie", "tasty cookie", new byte[2],
                CookieAddingStatus.WAITING, (float) 0, 1);

        when(cookieDao.update(cookieWithId)).thenReturn(cookieWithId);

        cookieService.update(cookie);

        verify(cookieDao).update(cookieWithId);

    }

    @Test
    public void testGetById () {
        when(cookieDao.getById(1)).thenReturn(cookieWithId);

        Cookie resultCookie = cookieService.getById(1);

        Assert.assertEquals(resultCookie.getId(), cookieWithId.getId());
        Assert.assertEquals(resultCookie.getName(), cookieWithId.getName());
        Assert.assertEquals(resultCookie.getDescription(), cookieWithId.getDescription());
        Assert.assertArrayEquals(resultCookie.getFileData(), cookieWithId.getFileData());
        Assert.assertEquals(resultCookie.getRating(), cookieWithId.getRating());
        Assert.assertEquals(resultCookie.getUserId(), cookieWithId.getUserId());

        verify(cookieDao).getById(1);
    }


}

