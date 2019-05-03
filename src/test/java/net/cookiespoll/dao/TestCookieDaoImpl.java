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

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TestCookieDaoImpl {

    @Mock
    CookieMapper cookieMapper;
    @InjectMocks
    CookieDaoImpl cookieDaoImpl;

    private Cookie cookie = new Cookie("cookie", "tasty cookie", new byte[2],
            CookieAddingStatus.WAITING, 0, 1);

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
        Integer rating = 0;
        Integer userId = 1;
        List<Cookie> cookies = new ArrayList<>();
        Cookie tastyCookie = new Cookie(1,"cookie", "tasty cookie", new byte[2],
                CookieAddingStatus.WAITING, 0, 1);
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







}
