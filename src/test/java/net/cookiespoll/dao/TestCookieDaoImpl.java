package net.cookiespoll.dao;

import net.cookiespoll.daoimpl.CookieDaoImpl;
import net.cookiespoll.dto.CookiesByParameterRequest;
import net.cookiespoll.mapper.CookieMapper;
import net.cookiespoll.model.Cookie;
import net.cookiespoll.model.CookieAddingStatus;
import net.cookiespoll.model.user.Role;
import net.cookiespoll.model.user.User;
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

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TestCookieDaoImpl {

    @Mock
    CookieMapper cookieMapper;
    @InjectMocks
    CookieDaoImpl cookieDaoImpl;

    private User cookieOwner = new User(1, "login", "name", Role.USER);
    private Cookie cookie = new Cookie("cookie", "tasty cookie", new byte[2],
            CookieAddingStatus.WAITING, 0, cookieOwner);

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCookieDaoInsert() {
        when(cookieMapper.insert(cookie, cookieOwner.getId())).thenReturn(1);

        Cookie resultCookie = cookieDaoImpl.insert(cookie);

        Assert.assertEquals (1, resultCookie.getId());
        Assert.assertEquals(cookie.getName(), resultCookie.getName());
        Assert.assertEquals(cookie.getDescription(), resultCookie.getDescription());
        Assert.assertArrayEquals(cookie.getFileData(), resultCookie.getFileData());
        Assert.assertEquals(cookie.getCookieAddingStatus(), resultCookie.getCookieAddingStatus());
        Assert.assertEquals(cookie.getRating(), resultCookie.getRating());
        Assert.assertEquals(cookie.getCookieOwner(), resultCookie.getCookieOwner());

        verify(cookieMapper).insert(cookie, cookieOwner.getId());
    }

    @Test
    public void testCookieDaoUpdate () {
        doNothing().when(cookieMapper).update(cookie);

        Cookie updatedCookie = cookieDaoImpl.update(cookie);

        verify(cookieMapper).update(cookie);
    }

    @Test
    public void testCookieDaoGetByParam () {
        CookiesByParameterRequest cookiesByParameterRequest = new CookiesByParameterRequest(1, "name",
                "description", CookieAddingStatus.WAITING, 0);
        List<Cookie> cookies = new ArrayList<>();
        Cookie tastyCookie = new Cookie(1,"cookie", "tasty cookie", new byte[2],
                CookieAddingStatus.WAITING, 0, cookieOwner);
        cookies.add(tastyCookie);

        when(cookieMapper.getByParam(cookiesByParameterRequest.getName(), cookiesByParameterRequest.getDescription(),
                                    cookiesByParameterRequest.getCookieAddingStatus(), cookiesByParameterRequest.getRating(),
                                    cookiesByParameterRequest.getUserId())).thenReturn(cookies);

        List<Cookie> resultList = cookieDaoImpl.getByParam(cookiesByParameterRequest);

        Assert.assertEquals(resultList.get(0).getId(), tastyCookie.getId());
        Assert.assertEquals(resultList.get(0).getName(), tastyCookie.getName());
        Assert.assertEquals(resultList.get(0).getDescription(), tastyCookie.getDescription());
        Assert.assertArrayEquals(resultList.get(0).getFileData(), tastyCookie.getFileData());
        Assert.assertEquals(resultList.get(0).getCookieAddingStatus(),
                            tastyCookie.getCookieAddingStatus());
        Assert.assertEquals(resultList.get(0).getRating(), tastyCookie.getRating());
        Assert.assertEquals(resultList.get(0).getCookieOwner(), tastyCookie.getCookieOwner());

        verify(cookieMapper).getByParam(cookiesByParameterRequest.getName(), cookiesByParameterRequest.getDescription(),
                cookiesByParameterRequest.getCookieAddingStatus(), cookiesByParameterRequest.getRating(),
                cookiesByParameterRequest.getUserId());
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
        Assert.assertEquals(resultCookie.getCookieOwner(), cookie.getCookieOwner());

        verify(cookieMapper).getById(1);
    }







}
