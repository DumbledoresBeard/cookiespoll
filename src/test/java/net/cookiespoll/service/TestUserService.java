package net.cookiespoll.service;

import net.cookiespoll.daoimpl.UserDaoImpl;
import net.cookiespoll.model.Cookie;
import net.cookiespoll.model.CookieAddingStatus;
import net.cookiespoll.model.CookieUserRating;
import net.cookiespoll.model.user.Role;
import net.cookiespoll.model.user.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TestUserService {
    @Mock
    UserDaoImpl userDao;

    @InjectMocks
    UserService userService;

    private String id = "1";
    private User userAdmin = new User("1", "login", "name", Role.ADMIN);
    private Cookie cookie = new Cookie("cookie", "tasty cookie", new byte[2], CookieAddingStatus.APPROVED,
            (float) 4, userAdmin);
    private List<CookieUserRating> ratedCookies = Arrays.asList(new CookieUserRating(userAdmin, cookie, 1));
    private List<Cookie> addedCookies = Arrays.asList(cookie);

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetUserRole() {
        Role admin = Role.ADMIN;

        when(userDao.getById(id)).thenReturn(userAdmin);

        Role resultRole = userService.getRole(id);

        Assert.assertEquals(admin, resultRole);

        verify(userDao).getById(id);
    }

    @Test
    public void testGetById() {
        userAdmin.setRatedCookies(ratedCookies);
        userAdmin.setAddedCookies(addedCookies);

        when(userDao.getById(id)).thenReturn(userAdmin);

        User resultUser = userService.getById(id);

        Assert.assertEquals(userAdmin.getId(), resultUser.getId());
        Assert.assertEquals(userAdmin.getLogin(), resultUser.getLogin());
        Assert.assertEquals(userAdmin.getName(), resultUser.getName());
        Assert.assertEquals(userAdmin.getRatedCookies(), resultUser.getRatedCookies());
        Assert.assertEquals(userAdmin.getAddedCookies(), resultUser.getAddedCookies());

        verify(userDao).getById(id);
    }

    @Test
    public void testUpdate() {
        userAdmin.setRatedCookies(ratedCookies);
        userAdmin.setAddedCookies(addedCookies);

        when(userDao.update(userAdmin)).thenReturn(userAdmin);

        User resultUser = userService.update(userAdmin);

        Assert.assertEquals(userAdmin.getId(), resultUser.getId());
        Assert.assertEquals(userAdmin.getLogin(), resultUser.getLogin());
        Assert.assertEquals(userAdmin.getName(), resultUser.getName());
        Assert.assertEquals(userAdmin.getRatedCookies(), resultUser.getRatedCookies());
        Assert.assertEquals(userAdmin.getAddedCookies(), resultUser.getAddedCookies());

        verify(userDao).update(userAdmin);
    }

}
