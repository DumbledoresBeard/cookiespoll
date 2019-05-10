package net.cookiespoll.service;

import net.cookiespoll.daoimpl.CookieDaoImpl;
import net.cookiespoll.dto.AddCookieRequest;
import net.cookiespoll.dto.CookiesByParameterRequest;
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
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TestCookieService {
    @Mock
    CookieDaoImpl cookieDao;
    @InjectMocks
    CookieService cookieService;

    private User cookieOwner = new User("1", "login", "name", Role.USER);
    private Float cookieRating = new Float(0);
    private Cookie cookie = new Cookie("cookie", "tasty cookie", new byte[2], CookieAddingStatus.WAITING,
            cookieRating, cookieOwner);
    private MockMultipartFile mockMultipartFile = new MockMultipartFile("testcookie.jpg", new byte[2]);
    private Cookie cookieWithId = new Cookie(1, "cookie", "tasty cookie", new byte[2],
            CookieAddingStatus.WAITING, cookieRating, cookieOwner);
    private AddCookieRequest addCookieRequest = new AddCookieRequest("cookie", "tasty cookie");
    private Cookie cookieWith2Id = new Cookie(2,"cookie", "tasty cookie", new byte[2],
            CookieAddingStatus.WAITING, cookieRating, new User("2", "login", "name", Role.USER));
    private String userId = "1";

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCookieDaoInsert() throws IOException {
        when(cookieDao.insert(cookie)).thenReturn(cookieWithId);

        Cookie resultCookie = cookieService.insert(addCookieRequest, mockMultipartFile, cookieOwner);

        Assert.assertEquals(1, resultCookie.getId());
        Assert.assertEquals(addCookieRequest.getName(), resultCookie.getName());
        Assert.assertEquals(addCookieRequest.getDescription(), resultCookie.getDescription());
        Assert.assertArrayEquals(mockMultipartFile.getBytes(), resultCookie.getFileData());
        Assert.assertEquals(cookie.getCookieAddingStatus(), resultCookie.getCookieAddingStatus());
        Assert.assertEquals(0, resultCookie.getRating(), 0.0f);
        Assert.assertEquals(1, resultCookie.getCookieOwner().getId());

        verify(cookieDao).insert(cookie);
    }

    @Test
    public void testGetCookiesByParam () {
        CookiesByParameterRequest cookiesByParameterRequest = new CookiesByParameterRequest("1", "name",
                "description", CookieAddingStatus.WAITING, cookieRating);
        List<Cookie> cookies = new ArrayList<>();
        cookies.add(cookieWithId);
        cookies.add(cookieWith2Id);

        when(cookieDao.getByParam(cookiesByParameterRequest)).thenReturn(cookies);

        List<Cookie> resultCookieList = cookieService.getByParam(cookiesByParameterRequest);

        Assert.assertEquals(resultCookieList.get(0).getId(), cookieWithId.getId());
        Assert.assertEquals(resultCookieList.get(0).getName(), cookieWithId.getName());
        Assert.assertEquals(resultCookieList.get(0).getDescription(), cookieWithId.getDescription());
        Assert.assertArrayEquals(resultCookieList.get(0).getFileData(), cookieWithId.getFileData());
        Assert.assertEquals(resultCookieList.get(0).getCookieAddingStatus(),
                            cookieWithId.getCookieAddingStatus());
        Assert.assertEquals(resultCookieList.get(0).getRating(), cookieWithId.getRating());
        Assert.assertEquals(resultCookieList.get(0).getCookieOwner(), cookieWithId.getCookieOwner());

        Assert.assertEquals(resultCookieList.get(1).getId(), cookieWith2Id.getId());
        Assert.assertEquals(resultCookieList.get(1).getName(), cookieWith2Id.getName());
        Assert.assertEquals(resultCookieList.get(1).getDescription(), cookieWith2Id.getDescription());
        Assert.assertArrayEquals(resultCookieList.get(1).getFileData(), cookieWith2Id.getFileData());
        Assert.assertEquals(resultCookieList.get(1).getCookieAddingStatus(),
                                    cookieWith2Id.getCookieAddingStatus());
        Assert.assertEquals(resultCookieList.get(1).getRating(), cookieWith2Id.getRating());
        Assert.assertEquals(resultCookieList.get(1).getCookieOwner(), cookieWith2Id.getCookieOwner());

        verify(cookieDao).getByParam(cookiesByParameterRequest);
    }

    @Test
    public void testUpdateCookie () {
        Cookie cookie = new Cookie(1, "cookie", "tasty cookie", new byte[2], CookieAddingStatus.WAITING,
                cookieRating, cookieOwner);

        when(cookieDao.update(cookieWithId)).thenReturn(cookieWithId);

        cookieService.update(cookie);

        verify(cookieDao).update(cookieWithId);
    }

    @Test
    public void testGetById () {
        when(cookieDao.getById(1)).thenReturn(cookieWithId);

        Cookie resultCookie = cookieService.getById(1);

        Assert.assertEquals(resultCookie.getId(), cookieWithId.getId());
        Assert.assertEquals(resultCookie.getName(), cookieWithId.getName());
        Assert.assertEquals(resultCookie.getDescription(), cookieWithId.getDescription());
        Assert.assertArrayEquals(resultCookie.getFileData(), cookieWithId.getFileData());
        Assert.assertEquals(resultCookie.getRating(), cookieWithId.getRating());
        Assert.assertEquals(resultCookie.getCookieOwner(), cookieWithId.getCookieOwner());

        verify(cookieDao).getById(1);
    }

    @Test
    public void testGetUnratedCookiesByUserId () {
        List<Cookie> cookies = new ArrayList<>();
        cookies.add(cookieWithId);
        cookies.add(cookieWith2Id);

        when(cookieDao.getUnratedCookiesByUserId(userId)).thenReturn(cookies);

        List<Cookie> resultCookieList = cookieService.getUnratedByUserId(userId);

        Assert.assertEquals(resultCookieList.get(0).getId(), cookieWithId.getId());
        Assert.assertEquals(resultCookieList.get(0).getName(), cookieWithId.getName());
        Assert.assertEquals(resultCookieList.get(0).getDescription(), cookieWithId.getDescription());
        Assert.assertArrayEquals(resultCookieList.get(0).getFileData(), cookieWithId.getFileData());
        Assert.assertEquals(resultCookieList.get(0).getCookieAddingStatus(),
                cookieWithId.getCookieAddingStatus());
        Assert.assertEquals(resultCookieList.get(0).getRating(), cookieWithId.getRating());

        Assert.assertEquals(resultCookieList.get(1).getId(), cookieWith2Id.getId());
        Assert.assertEquals(resultCookieList.get(1).getName(), cookieWith2Id.getName());
        Assert.assertEquals(resultCookieList.get(1).getDescription(), cookieWith2Id.getDescription());
        Assert.assertArrayEquals(resultCookieList.get(1).getFileData(), cookieWith2Id.getFileData());
        Assert.assertEquals(resultCookieList.get(1).getCookieAddingStatus(),
                cookieWith2Id.getCookieAddingStatus());
        Assert.assertEquals(resultCookieList.get(1).getRating(), cookieWith2Id.getRating());

        verify(cookieDao).getUnratedCookiesByUserId(userId);
    }

    @Test
    public void testCountRating () {
        Integer usersQuantity = 10;
        float cookieRatingSum = 34;
        float rating = (float) 3.4;

        float resultRating = cookieService.countRating(usersQuantity, cookieRatingSum);

        Assert.assertEquals(rating, resultRating, 0.0f);
    }


}

