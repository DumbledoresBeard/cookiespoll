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
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

public class TestUserService {

    private UserDaoImpl userDao = mock(UserDaoImpl.class);
    private EmailDomenValidator emailDomenValidator = new EmailDomenValidator();
    private UserService userService;

    @Rule
    public ExpectedException exception = ExpectedException.none();

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
    public void testProcessOidcUserValidEmailDomen() throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
        User user = new User("12345", "a@lineate.com", "name", Role.USER);
        Instant issuedAt = Instant.now();
        Instant expiresAt = Instant.now().plusSeconds(10000);
        OidcIdToken oidcIdToken = new OidcIdToken("token", issuedAt, expiresAt, Map.of("sub", "12345", "name", "name",
                "email", "a@lineate.com"));
        OAuth2AccessToken accessToken = new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER, "token", issuedAt, expiresAt);
        ClientRegistration clientRegistration = null;

        Constructor<ClientRegistration> constructor = ClientRegistration.class.getDeclaredConstructor();
        if (Modifier.isPrivate(constructor.getModifiers())) {
            constructor.setAccessible(true);
            clientRegistration = (ClientRegistration)constructor.newInstance();
        }

        OidcUserRequest oidcUserRequest = new OidcUserRequest(clientRegistration, accessToken, oidcIdToken);

        when(userDao.getById("12345")).thenReturn(null);
        when(userDao.insert(user)).thenReturn(user);
        when(userDao.getAdmins()).thenReturn(new ArrayList<>());

        OidcUser oidcUser = userService.loadUser(oidcUserRequest);

        Assert.assertEquals(oidcUser.getClaims().get("sub"), user.getId());
        Assert.assertEquals(oidcUser.getClaims().get("email"), user.getLogin());
        Assert.assertEquals(oidcUser.getClaims().get("name"), user.getName());

        verify(userDao).getById("12345");
        verify(userDao).insert(user);
        verify(userDao).getAdmins();
    }

    @Test
    public void testProcessOidcUserInvalidEmailDomen() throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
        User user = new User("12345", "a@mail.com", "name", Role.USER);
        Instant issuedAt = Instant.now();
        Instant expiresAt = Instant.now().plusSeconds(10000);
        OidcIdToken oidcIdToken = new OidcIdToken("token", issuedAt, expiresAt, Map.of("sub", "12345", "name", "name",
                "email", "a@mail.com"));
        OAuth2AccessToken accessToken = new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER, "token", issuedAt, expiresAt);
        ClientRegistration clientRegistration = null;

        Constructor<ClientRegistration> constructor = ClientRegistration.class.getDeclaredConstructor();
        if (Modifier.isPrivate(constructor.getModifiers())) {
            constructor.setAccessible(true);
            clientRegistration = (ClientRegistration)constructor.newInstance();
        }

        OidcUserRequest oidcUserRequest = new OidcUserRequest(clientRegistration, accessToken, oidcIdToken);

        exception.expect(InternalAuthenticationServiceException.class);
        exception.expectMessage("Email with this domen cannot be logged in");

        userService.loadUser(oidcUserRequest);
    }
}
