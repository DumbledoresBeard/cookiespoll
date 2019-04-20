package net.cookiespoll.service;
import net.cookiespoll.daoimpl.CookieDaoImpl;
import net.cookiespoll.daoimpl.UserDaoImpl;
import net.cookiespoll.dto.AddCookieDtoRequest;
import net.cookiespoll.model.Cookie;
import net.cookiespoll.model.CookieAddingStatus;
import net.cookiespoll.model.User;
import net.cookiespoll.model.UserRole;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TestCookieService {
    @Mock
    CookieDaoImpl cookieDao;
    @Mock
    UserDaoImpl userDao;
    @InjectMocks
    CookieService cookieService;

    private Cookie cookie = new Cookie("cookie", "tasty cookie", new byte[2], CookieAddingStatus.WAITING);
    private Cookie cookieWithId = new Cookie(1, "cookie", "tasty cookie", new byte[2],
            CookieAddingStatus.WAITING, 0);
    private Cookie cookieWith2Id = new Cookie(2,"cookie", "tasty cookie", new byte[2],
            CookieAddingStatus.WAITING, 0);
    private MockMultipartFile mockMultipartFile = new MockMultipartFile("testcookie.jpg", new byte[2]);
    private AddCookieDtoRequest addCookieDtoRequest = new AddCookieDtoRequest("cookie", "tasty cookie");
    private int id = 1;
    private User userAdmin = new User (1, "login", "password", "name", "lastname",
                    UserRole.ADMIN);
    private UserRole admin = UserRole.ADMIN;
    private CookieAddingStatus cookieAddingStatus = CookieAddingStatus.WAITING;
    private List<Cookie> cookies = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCookieDaoInsert() throws IOException {

        when(cookieDao.insert(cookie)).thenReturn(cookieWithId);
        Cookie resultCookie = cookieService.addCookie(addCookieDtoRequest, mockMultipartFile);

        Assert.assertEquals(1, resultCookie.getId());
        Assert.assertEquals(addCookieDtoRequest.getName(), resultCookie.getName());
        Assert.assertEquals(addCookieDtoRequest.getDescription(), resultCookie.getDescription());
        Assert.assertArrayEquals(mockMultipartFile.getBytes(), resultCookie.getFileData());
        Assert.assertEquals(cookie.getCookieAddingStatus(), resultCookie.getCookieAddingStatus());
        Assert.assertEquals(0, resultCookie.getRating());

        verify(cookieDao).insert(cookie);

    }

    @Test
    public void testGetUserRole() {
        when(userDao.getUserById(id)).thenReturn(userAdmin);

        UserRole resultUserRole = cookieService.getUserRole(id);

        Assert.assertEquals(admin, resultUserRole);

        verify(userDao).getUserById(id);

    }

    @Test
    public void testGetCookieListByAddingStatus () {
        cookies.add(cookieWithId);
        cookies.add(cookieWith2Id);
        when(cookieDao.getCookieListByCookieAddingStatus(cookieAddingStatus)).thenReturn(cookies);

        List<Cookie> resultCookieList = cookieService.getCookieListByAddingStatus(cookieAddingStatus);

        Assert.assertEquals(resultCookieList.get(0).getId(), cookieWithId.getId());
        Assert.assertEquals(resultCookieList.get(0).getName(), cookieWithId.getName());
        Assert.assertEquals(resultCookieList.get(0).getDescription(), cookieWithId.getDescription());
        Assert.assertArrayEquals(resultCookieList.get(0).getFileData(), cookieWithId.getFileData());
        Assert.assertEquals(resultCookieList.get(0).getCookieAddingStatus(), cookieWithId.getCookieAddingStatus());
        Assert.assertEquals(resultCookieList.get(0).getRating(), cookieWithId.getRating());

        Assert.assertEquals(resultCookieList.get(1).getId(), cookieWith2Id.getId());
        Assert.assertEquals(resultCookieList.get(1).getName(), cookieWith2Id.getName());
        Assert.assertEquals(resultCookieList.get(1).getDescription(), cookieWith2Id.getDescription());
        Assert.assertArrayEquals(resultCookieList.get(1).getFileData(), cookieWith2Id.getFileData());
        Assert.assertEquals(resultCookieList.get(1).getCookieAddingStatus(), cookieWith2Id.getCookieAddingStatus());
        Assert.assertEquals(resultCookieList.get(1).getRating(), cookieWith2Id.getRating());

        verify(cookieDao).getCookieListByCookieAddingStatus(cookieAddingStatus);

    }

}
