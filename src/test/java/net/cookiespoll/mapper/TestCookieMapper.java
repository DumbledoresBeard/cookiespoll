package net.cookiespoll.mapper;

import net.cookiespoll.model.Cookie;
import net.cookiespoll.model.CookieAddingStatus;
import net.cookiespoll.model.user.Role;
import net.cookiespoll.model.user.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TestCookieMapper {

    @Mock
    CookieMapper cookieMapper;

    private User cookieOwner = new User(1, "login", "name", Role.USER);
    Cookie cookie = new Cookie("cookie", "tasty cookie", new byte[2], CookieAddingStatus.WAITING,
                                0, cookieOwner);


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testCookieMapperInsert() {
        when(cookieMapper.insert(cookie, cookieOwner.getId())).thenReturn(1);

        assert cookieMapper.insert(cookie, cookieOwner.getId()) == 1;

        verify(cookieMapper).insert(cookie, cookieOwner.getId());

    }

    @Test
    public void testCookieMapperUpdate () {
        doNothing().when(cookieMapper).update(cookie);

        cookieMapper.update(cookie);

        verify(cookieMapper).update(cookie);
    }

    @Test
    public void testCookieMapperGetByParam () {
        List<Cookie> cookies = new ArrayList<>();
        cookies.add(cookie);

        when(cookieMapper.getByParam("cookie", "tasty cookie", CookieAddingStatus.WAITING,
                0, 1)).thenReturn(cookies);

        Assert.assertEquals(cookieMapper.getByParam("cookie", "tasty cookie", CookieAddingStatus.WAITING,
                0, 1).get(0).getId(), cookie.getId());

        verify(cookieMapper).getByParam("cookie", "tasty cookie", CookieAddingStatus.WAITING,
                0, 1);
    }

    @Test
    public void testCookieMapperGetById () {
        when(cookieMapper.getById(1)).thenReturn(cookie);

        Assert.assertEquals(cookieMapper.getById(1).getId(), cookie.getId());

        verify(cookieMapper).getById(1);
    }
}
