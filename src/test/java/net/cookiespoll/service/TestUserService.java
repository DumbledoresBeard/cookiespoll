package net.cookiespoll.service;

import net.cookiespoll.daoimpl.UserDaoImpl;
import net.cookiespoll.model.Cookie;
import net.cookiespoll.model.CookieAddingStatus;
import net.cookiespoll.model.CookieUserRating;
import net.cookiespoll.model.user.Role;
import net.cookiespoll.model.user.User;
import net.cookiespoll.validation.EmailDomenValidator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

public class TestUserService {

    private UserDaoImpl userDao = mock(UserDaoImpl.class);
    private EmailDomenValidator emailDomenValidator = new EmailDomenValidator();
    private UserService userService;



    private String id = "1";
    private User userAdmin = new User("1", "login", "name", Role.ADMIN);
    private Cookie cookie = new Cookie("cookie", "tasty cookie", new byte[2], CookieAddingStatus.APPROVED,
            (float) 4, userAdmin);
    private List<CookieUserRating> ratedCookies = Arrays.asList(new CookieUserRating(userAdmin, cookie, 1));
    private List<Cookie> addedCookies = Arrays.asList(cookie);

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        userService = new UserService(userDao, emailDomenValidator);
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

    @Test
    public void testProcessOidcUser() throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
        Instant issuedAt = Instant.now();
        Instant expiresAt = Instant.now();
        OidcIdToken oidcIdToken = new OidcIdToken("token", issuedAt, expiresAt, Map.of("sub", "12345"));
        OAuth2AccessToken accessToken = new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER, "token", issuedAt, expiresAt);
        ClientRegistration clientRegistration = null;

        Constructor<ClientRegistration> constructor = ClientRegistration.class.getDeclaredConstructor();
        if (Modifier.isPrivate(constructor.getModifiers())) {
            constructor.setAccessible(true);
            clientRegistration = (ClientRegistration)constructor.newInstance();
        }


    }

}
