package net.cookiespoll.mapper;

import net.cookiespoll.model.Cookie;
import net.cookiespoll.model.CookieAddingStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TestCookieMapper {

    @Mock
    private CookieMapper cookieMapper;

    Cookie cookie = new Cookie("cookie", "tasty cookie", new byte[2], CookieAddingStatus.WAITING,
            (float) 0, 1);


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testCookieMapperInsert() {
        when(cookieMapper.insert(cookie)).thenReturn(1);
        assert cookieMapper.insert(cookie) == 1;
        verify(cookieMapper).insert(cookie);

    }

    @Test
    public void testCookieMapperUpdate () {
        doNothing().when(cookieMapper).update(cookie);
        cookieMapper.insert(cookie);
        verify(cookieMapper).update(cookie);
    }

    @Test
    public void testCookieMapperGetByParam () {
        List<Cookie> cookies = new ArrayList<>();
        cookies.add(cookie);

        when(cookieMapper.getByParam("cookie", "tasty cookie", CookieAddingStatus.WAITING,
                (float) 0, 1)).thenReturn(cookies);

        Assert.assertEquals(cookieMapper.getByParam("cookie", "tasty cookie", CookieAddingStatus.WAITING,
                (float) 0, 1).get(0).getId(), cookie.getId());

        verify(cookieMapper).getByParam("cookie", "tasty cookie", CookieAddingStatus.WAITING,
                (float) 0, 1);
    }

    @Test
    public void testCookieMapperGetById () {

        when(cookieMapper.getById(1)).thenReturn(cookie);

        Assert.assertEquals(cookieMapper.getById(1).getId(), cookie.getId());

        verify(cookieMapper).getById(1);
    }

    @Test
    public void testGetUnratedCookiesByUserId () {
        List<Cookie> cookies = new ArrayList<>();
        Cookie cookieWith1Id = new Cookie(1, "cookie", "tasty cookie",
                new byte[2], CookieAddingStatus.WAITING, (float) 0, 1);
        Cookie cookieWith2Id = new Cookie(2,"name", "description", new byte[2],
                CookieAddingStatus.WAITING, (float) 0, 1);
        cookies.add(cookieWith1Id);
        cookies.add(cookieWith2Id);

        when(cookieMapper.getUnratedCookiesByUserId(1)).thenReturn(cookies);

        Assert.assertEquals(cookies, cookieMapper.getUnratedCookiesByUserId(1));

        verify(cookieMapper).getUnratedCookiesByUserId(1);
    }
}
