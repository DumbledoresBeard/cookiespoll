/*
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
    private CookieMapper cookieMapper;
    @InjectMocks
    private CookieDaoImpl cookieDaoImpl;

    private User cookieOwner = new User(1, "login", "name", Role.USER);
    private Float cookieRating = new Float(0);
    private Cookie cookie = new Cookie("cookie", "tasty cookie", new byte[2],
            CookieAddingStatus.WAITING, cookieRating, cookieOwner);

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCookieDaoInsert() {
        when(cookieMapper.insert(cookie, cookieOwner.getCookieId())).thenReturn(1);

        Cookie resultCookie = cookieDaoImpl.insert(cookie);

        Assert.assertEquals (1, resultCookie.getCookieId());
        Assert.assertEquals(cookie.getName(), resultCookie.getName());
        Assert.assertEquals(cookie.getDescription(), resultCookie.getDescription());
        Assert.assertArrayEquals(cookie.getFileData(), resultCookie.getFileData());
        Assert.assertEquals(cookie.getCookieAddingStatus(), resultCookie.getCookieAddingStatus());
        Assert.assertEquals(cookie.getRating(), resultCookie.getRating());
        Assert.assertEquals(cookie.getCookieOwner(), resultCookie.getCookieOwner());

        verify(cookieMapper).insert(cookie, cookieOwner.getCookieId());
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
                "description", CookieAddingStatus.WAITING, cookieRating);
        List<Cookie> cookies = new ArrayList<>();
        Cookie tastyCookie = new Cookie(1,"cookie", "tasty cookie", new byte[2],
                CookieAddingStatus.WAITING, cookieRating, cookieOwner);

        cookies.add(tastyCookie);

        when(cookieMapper.getByParam(cookiesByParameterRequest.getName(), cookiesByParameterRequest.getDescription(),
                                    cookiesByParameterRequest.getCookieAddingStatus(), cookiesByParameterRequest.getRating(),
                                    cookiesByParameterRequest.getCookieId())).thenReturn(cookies);

        List<Cookie> resultList = cookieDaoImpl.getByParam(cookiesByParameterRequest);

        Assert.assertEquals(resultList.get(0).getCookieId(), tastyCookie.getCookieId());
        Assert.assertEquals(resultList.get(0).getName(), tastyCookie.getName());
        Assert.assertEquals(resultList.get(0).getDescription(), tastyCookie.getDescription());
        Assert.assertArrayEquals(resultList.get(0).getFileData(), tastyCookie.getFileData());
        Assert.assertEquals(resultList.get(0).getCookieAddingStatus(),
                            tastyCookie.getCookieAddingStatus());
        Assert.assertEquals(resultList.get(0).getRating(), tastyCookie.getRating());
        Assert.assertEquals(resultList.get(0).getCookieOwner(), tastyCookie.getCookieOwner());

        verify(cookieMapper).getByParam(cookiesByParameterRequest.getName(), cookiesByParameterRequest.getDescription(),
                cookiesByParameterRequest.getCookieAddingStatus(), cookiesByParameterRequest.getRating(),
                cookiesByParameterRequest.getCookieId());
    }

    @Test
    public void testCookieDaoGetById () {
        when(cookieMapper.getById(1)).thenReturn(cookie);

        Cookie resultCookie = cookieDaoImpl.getById(1);

        Assert.assertEquals(resultCookie.getCookieId(), cookie.getCookieId());
        Assert.assertEquals(resultCookie.getName(), cookie.getName());
        Assert.assertEquals(resultCookie.getDescription(), cookie.getDescription());
        Assert.assertArrayEquals(resultCookie.getFileData(), cookie.getFileData());
        Assert.assertEquals(resultCookie.getCookieAddingStatus(), cookie.getCookieAddingStatus());
        Assert.assertEquals(resultCookie.getRating(), cookie.getRating());
        Assert.assertEquals(resultCookie.getCookieOwner(), cookie.getCookieOwner());

        verify(cookieMapper).getById(1);
    }

    @Test
    public void testGetUnratedCookiesByUserId () {
        List<Cookie> cookies = new ArrayList<>();
        Cookie cookieWith1Id = new Cookie(1, "cookie", "tasty cookie",
                new byte[2], CookieAddingStatus.WAITING, cookieRating, cookieOwner);
        Cookie cookieWith2Id = new Cookie(2,"name", "description", new byte[2],
                CookieAddingStatus.WAITING, cookieRating, cookieOwner);
        cookies.add(cookieWith1Id);
        cookies.add(cookieWith2Id);

        when(cookieMapper.getUnratedByUserId(1)).thenReturn(cookies);

        List<Cookie> resultList = cookieDaoImpl.getUnratedByUserId(1);

        Assert.assertEquals(resultList.get(0).getCookieId(), cookieWith1Id.getCookieId());
        Assert.assertEquals(resultList.get(0).getName(), cookieWith1Id.getName());
        Assert.assertEquals(resultList.get(0).getDescription(), cookieWith1Id.getDescription());
        Assert.assertArrayEquals(resultList.get(0).getFileData(), cookieWith1Id.getFileData());
        Assert.assertEquals(resultList.get(0).getCookieAddingStatus(),
                cookieWith1Id.getCookieAddingStatus());
        Assert.assertEquals(resultList.get(0).getRating(), cookieWith1Id.getRating());
        Assert.assertEquals(resultList.get(0).getCookieOwner(), cookieWith1Id.getCookieOwner());

        Assert.assertEquals(resultList.get(1).getCookieId(), cookieWith2Id.getCookieId());
        Assert.assertEquals(resultList.get(1).getName(), cookieWith2Id.getName());
        Assert.assertEquals(resultList.get(1).getDescription(), cookieWith2Id.getDescription());
        Assert.assertArrayEquals(resultList.get(1).getFileData(), cookieWith2Id.getFileData());
        Assert.assertEquals(resultList.get(1).getCookieAddingStatus(),
                cookieWith2Id.getCookieAddingStatus());
        Assert.assertEquals(resultList.get(1).getRating(), cookieWith2Id.getRating());
        Assert.assertEquals(resultList.get(1).getCookieOwner(), cookieWith2Id.getCookieOwner());

        verify(cookieMapper).getUnratedByUserId(1);

    }







}
*/
