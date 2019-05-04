package net.cookiespoll.dao;

import net.cookiespoll.daoimpl.CookieDaoImpl;
import net.cookiespoll.mapper.CookieMapper;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.floatThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TestCookieDaoImpl {

    @Mock
    private CookieMapper cookieMapper;
    @InjectMocks
    private CookieDaoImpl cookieDaoImpl;

    private Cookie cookie = new Cookie("cookie", "tasty cookie", new byte[2],
            CookieAddingStatus.WAITING, (float) 0, "1");

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCookieDaoInsert() {
        when(cookieMapper.insert(cookie)).thenReturn(1);
        Cookie resultCookie = cookieDaoImpl.insert(cookie);

        Assert.assertEquals (1, resultCookie.getId());
        Assert.assertEquals(cookie.getName(), resultCookie.getName());
        Assert.assertEquals(cookie.getDescription(), resultCookie.getDescription());
        Assert.assertArrayEquals(cookie.getFileData(), resultCookie.getFileData());
        Assert.assertEquals(cookie.getCookieAddingStatus(), resultCookie.getCookieAddingStatus());
        Assert.assertEquals(cookie.getRating(), resultCookie.getRating());
        Assert.assertEquals(cookie.getUserId(), resultCookie.getUserId());

        verify(cookieMapper).insert(cookie);

    }

    @Test
    public void testCookieDaoUpdate () {
        doNothing().when(cookieMapper).update(cookie);
        Cookie updatedCookie = cookieDaoImpl.update(cookie);
        verify(cookieMapper).update(cookie);
    }

    @Test
    public void testCookieDaoGetByParam () {
        String name = "name";
        String description = "tasty cookie";
        CookieAddingStatus cookieAddingStatus = CookieAddingStatus.WAITING;
        float rating = 0;
        String userId = "1";
        List<Cookie> cookies = new ArrayList<>();
        Cookie tastyCookie = new Cookie(1,"cookie", "tasty cookie", new byte[2],
                CookieAddingStatus.WAITING, (float) 0, "1");
        cookies.add(tastyCookie);

        when(cookieMapper.getByParam(name, description, cookieAddingStatus, rating, userId))
                .thenReturn(cookies);

        List<Cookie> resultList = cookieDaoImpl.getByParam(name, description, cookieAddingStatus,
                rating, userId);

        Assert.assertEquals(resultList.get(0).getId(), tastyCookie.getId());
        Assert.assertEquals(resultList.get(0).getName(), tastyCookie.getName());
        Assert.assertEquals(resultList.get(0).getDescription(), tastyCookie.getDescription());
        Assert.assertArrayEquals(resultList.get(0).getFileData(), tastyCookie.getFileData());
        Assert.assertEquals(resultList.get(0).getCookieAddingStatus(),
                            tastyCookie.getCookieAddingStatus());
        Assert.assertEquals(resultList.get(0).getRating(), tastyCookie.getRating());
        Assert.assertEquals(resultList.get(0).getUserId(), tastyCookie.getUserId());

        verify(cookieMapper).getByParam(name, description, cookieAddingStatus, rating, userId);
    }

    @Test
    public void testCookieDaoGetById () {
        when(cookieMapper.getById(1)).thenReturn(cookie);

        Cookie resultCookie = cookieDaoImpl.getById(1);

        Assert.assertEquals(resultCookie.getId(), cookie.getId());
        Assert.assertEquals(resultCookie.getName(), cookie.getName());
        Assert.assertEquals(resultCookie.getDescription(), cookie.getDescription());
        Assert.assertArrayEquals(resultCookie.getFileData(), cookie.getFileData());
        Assert.assertEquals(resultCookie.getCookieAddingStatus(), cookie.getCookieAddingStatus());
        Assert.assertEquals(resultCookie.getRating(), cookie.getRating());
        Assert.assertEquals(resultCookie.getUserId(), cookie.getUserId());

        verify(cookieMapper).getById(1);
    }

    @Test
    public void testGetUnratedCookiesByUserId () {
        List<Cookie> cookies = new ArrayList<>();
        Cookie cookieWith1Id = new Cookie(1, "cookie", "tasty cookie",
                new byte[2], CookieAddingStatus.WAITING, (float) 0, "1");
        Cookie cookieWith2Id = new Cookie(2,"name", "description", new byte[2],
                CookieAddingStatus.WAITING, (float) 0, "1");
        cookies.add(cookieWith1Id);
        cookies.add(cookieWith2Id);
        String userId = "1";

        when(cookieMapper.getUnratedCookiesByUserId(userId)).thenReturn(cookies);

        List<Cookie> resultList = cookieDaoImpl.getUnratedCookiesByUserId(userId);

        Assert.assertEquals(resultList.get(0).getId(), cookieWith1Id.getId());
        Assert.assertEquals(resultList.get(0).getName(), cookieWith1Id.getName());
        Assert.assertEquals(resultList.get(0).getDescription(), cookieWith1Id.getDescription());
        Assert.assertArrayEquals(resultList.get(0).getFileData(), cookieWith1Id.getFileData());
        Assert.assertEquals(resultList.get(0).getCookieAddingStatus(),
                cookieWith1Id.getCookieAddingStatus());
        Assert.assertEquals(resultList.get(0).getRating(), cookieWith1Id.getRating());
        Assert.assertEquals(resultList.get(0).getUserId(), cookieWith1Id.getUserId());

        Assert.assertEquals(resultList.get(1).getId(), cookieWith2Id.getId());
        Assert.assertEquals(resultList.get(1).getName(), cookieWith2Id.getName());
        Assert.assertEquals(resultList.get(1).getDescription(), cookieWith2Id.getDescription());
        Assert.assertArrayEquals(resultList.get(1).getFileData(), cookieWith2Id.getFileData());
        Assert.assertEquals(resultList.get(1).getCookieAddingStatus(),
                cookieWith2Id.getCookieAddingStatus());
        Assert.assertEquals(resultList.get(1).getRating(), cookieWith2Id.getRating());
        Assert.assertEquals(resultList.get(1).getUserId(), cookieWith2Id.getUserId());

        verify(cookieMapper).getUnratedCookiesByUserId(userId);

    }







}
