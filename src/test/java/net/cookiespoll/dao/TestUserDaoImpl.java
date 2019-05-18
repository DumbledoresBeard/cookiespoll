package net.cookiespoll.dao;

import net.cookiespoll.daoimpl.UserDaoImpl;
import net.cookiespoll.mapper.UserMapper;
import net.cookiespoll.model.Cookie;
import net.cookiespoll.model.CookieAddingStatus;
import net.cookiespoll.model.CookieUserRating;
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
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TestUserDaoImpl {

    @Mock
    UserMapper userMapper;
    @InjectMocks
    UserDaoImpl userDaoImpl;

    private int id = 1;
    private User userAdmin = new User(1, "login", "name", Role.ADMIN);
    private Cookie cookie = new Cookie("cookie", "tasty cookie", new byte[2], CookieAddingStatus.APPROVED,
            (float) 4, userAdmin);
    private List<CookieUserRating> ratedCookies = Arrays.asList(new CookieUserRating(userAdmin, cookie, 1));
    private List<Cookie> addedCookies = Arrays.asList(cookie);

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testUserDaoGetUserById() {
        userAdmin.setRatedCookies(ratedCookies);
        userAdmin.setAddedCookies(addedCookies);

        when(userMapper.getById(id)).thenReturn(userAdmin);

        User resultUser = userDaoImpl.getById(id);

        Assert.assertEquals(userAdmin.getId(), resultUser.getId());
        Assert.assertEquals(userAdmin.getLogin(), resultUser.getLogin());
        Assert.assertEquals(userAdmin.getName(), resultUser.getName());
        Assert.assertEquals(userAdmin.getRole(), resultUser.getRole());
        Assert.assertEquals(userAdmin.getRatedCookies(), resultUser.getRatedCookies());
        Assert.assertEquals(userAdmin.getAddedCookies(), resultUser.getAddedCookies());

        verify(userMapper).getById(id);
    }

    @Test
    public void testUserDaoUpdateUser() {
        userAdmin.setRatedCookies(ratedCookies);
        userAdmin.setAddedCookies(addedCookies);

        doNothing().when(userMapper).update(userAdmin);

        User resultUser = userDaoImpl.update(userAdmin);

        Assert.assertEquals(userAdmin.getId(), resultUser.getId());
        Assert.assertEquals(userAdmin.getLogin(), resultUser.getLogin());
        Assert.assertEquals(userAdmin.getName(), resultUser.getName());
        Assert.assertEquals(userAdmin.getRole(), resultUser.getRole());
        Assert.assertEquals(userAdmin.getRatedCookies(), resultUser.getRatedCookies());
        Assert.assertEquals(userAdmin.getAddedCookies(), resultUser.getAddedCookies());

        verify(userMapper).update(userAdmin);
    }

}
