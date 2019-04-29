package net.cookiespoll.service;
import net.cookiespoll.daoimpl.CookieDaoImpl;
import net.cookiespoll.daoimpl.UserDaoImpl;
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
@Ignore
@RunWith(MockitoJUnitRunner.class)
public class TestCookieService {
    @Mock
    CookieDaoImpl cookieDao;
    @InjectMocks
    CookieService cookieService;

    private Cookie cookie = new Cookie("cookie", "tasty cookie", new byte[2], CookieAddingStatus.WAITING,
                                    0, 1);
    private MockMultipartFile mockMultipartFile = new MockMultipartFile("testcookie.jpg", new byte[2]);
    private Cookie cookieWithId = new Cookie(1, "cookie", "tasty cookie", new byte[2],
            CookieAddingStatus.WAITING, 0, 1);
    private AddCookieRequest addCookieRequest = new AddCookieRequest("cookie", "tasty cookie");
    private Integer userId = 1;
    private Integer id = 1;
    private String name = "name";
    private String description = "description";
    private CookieAddingStatus cookieAddingStatus = CookieAddingStatus.WAITING;
    private Integer rating = 0;
    List<Cookie> cookies = new ArrayList<>();
    Cookie cookieWith2Id = new Cookie(2,"cookie", "tasty cookie", new byte[2],
            CookieAddingStatus.WAITING, 0, 2);



    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCookieDaoInsert() throws IOException {

        when(cookieDao.insert(cookie)).thenReturn(cookieWithId);
        Cookie resultCookie = cookieService.addCookie(addCookieRequest, mockMultipartFile, userId);

        Assert.assertEquals(1, resultCookie.getId());
        Assert.assertEquals(addCookieRequest.getName(), resultCookie.getName());
        Assert.assertEquals(addCookieRequest.getDescription(), resultCookie.getDescription());
        Assert.assertArrayEquals(mockMultipartFile.getBytes(), resultCookie.getFileData());
        Assert.assertEquals(cookie.getCookieAddingStatus(), resultCookie.getCookieAddingStatus());
        Assert.assertEquals(0, resultCookie.getRating());
        Assert.assertEquals(1, resultCookie.getUserId());

        verify(cookieDao).insert(cookie);

    }




    @Test
    public void testGetCookiesByParam () {
        cookies.add(cookieWithId);
        cookies.add(cookieWith2Id);

        when(cookieDao.getByParam(name, description, cookieAddingStatus, rating, userId)).thenReturn(cookies);

        List<Cookie> resultCookieList = cookieService.getCookiesByParam(name, description, cookieAddingStatus,
                                        rating, userId);

        Assert.assertEquals(resultCookieList.get(0).getId(), cookieWithId.getId());
        Assert.assertEquals(resultCookieList.get(0).getName(), cookieWithId.getName());
        Assert.assertEquals(resultCookieList.get(0).getDescription(), cookieWithId.getDescription());
        Assert.assertArrayEquals(resultCookieList.get(0).getFileData(), cookieWithId.getFileData());
        Assert.assertEquals(resultCookieList.get(0).getCookieAddingStatus(), cookieWithId.getCookieAddingStatus());
        Assert.assertEquals(resultCookieList.get(0).getRating(), cookieWithId.getRating());

        Assert.assertEquals(resultCookieList.get(1).getId(), cookieWith2Id.getId());
        Assert.assertEquals(resultCookieList.get(1).getName(), cookieWith2Id.getName());
        Assert.assertEquals(resultCookieList.get(1).getDescription(), cookieWith2Id.getDescription());
        Assert.assertArrayEquals(resultCookieList.get(1).getFileData(), cookieWith2Id.getFileData());
        Assert.assertEquals(resultCookieList.get(1).getCookieAddingStatus(), cookieWith2Id.getCookieAddingStatus());
        Assert.assertEquals(resultCookieList.get(1).getRating(), cookieWith2Id.getRating());

        verify(cookieDao).getByParam(name, description, cookieAddingStatus, rating, userId);

    }

    @Test
    public void testUpdateCookie () {
        UpdateCookieRequest updateCookieRequest = new UpdateCookieRequest(
                1, "cookie", "tasty cookie", new byte[2],
                CookieAddingStatus.WAITING, 0, 1);

        when(cookieDao.update(cookieWithId)).thenReturn(cookieWithId);

        cookieService.updateCookie(updateCookieRequest);


    }



}

