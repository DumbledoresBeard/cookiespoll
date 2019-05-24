package net.cookiespoll.service;

import net.cookiespoll.daoimpl.CookieDaoImpl;
import net.cookiespoll.dto.AddCookieRequest;
import net.cookiespoll.dto.CookiesByParameterRequest;
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
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TestCookieService {
    @Mock
    CookieDaoImpl cookieDao;
    @InjectMocks
    CookieService cookieService;

    private User cookieOwner = new User(1, "login", "name", Role.USER);
    private Float cookieRating = new Float(0);
    private Cookie cookie = new Cookie("cookie", "tasty cookie", new byte[2], CookieAddingStatus.WAITING,
            cookieRating, cookieOwner);
    private MockMultipartFile mockMultipartFile = new MockMultipartFile("testcookie.jpg", new byte[2]);
    private Cookie cookieWithId = new Cookie(1, "cookie", "tasty cookie", new byte[2],
            CookieAddingStatus.WAITING, cookieRating, cookieOwner);
    private AddCookieRequest addCookieRequest = new AddCookieRequest("cookie", "tasty cookie");
    Cookie cookieWith2Id = new Cookie(2,"cookie", "tasty cookie", new byte[2],
            CookieAddingStatus.WAITING, cookieRating, new User(2, "login", "name", Role.USER));
    private Integer userId = 1;
    private User userAdmin = new User(1, "login", "name", Role.ADMIN);
    private  List<CookieUserRating> usersRatings = Arrays.asList(new CookieUserRating(userAdmin, cookie, 3));

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testCookieDaoInsert() throws IOException {
        when(cookieDao.insert(cookie)).thenReturn(cookieWithId);

        Cookie resultCookie = cookieService.insert(addCookieRequest, mockMultipartFile, cookieOwner);

        Assert.assertEquals(1, resultCookie.getCookieId());
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
        CookiesByParameterRequest cookiesByParameterRequest = new CookiesByParameterRequest(1, "name",
                "description", CookieAddingStatus.WAITING, cookieRating);
        List<Cookie> cookies = new ArrayList<>();
        cookieWithId.setUsersRatings(usersRatings);
        cookieWith2Id.setUsersRatings(usersRatings);
        cookies.add(cookieWithId);
        cookies.add(cookieWith2Id);

        when(cookieDao.getByParam(cookiesByParameterRequest.getName(), cookiesByParameterRequest.getDescription(),
                cookiesByParameterRequest.getCookieAddingStatus(), cookiesByParameterRequest.getRating(),
                cookiesByParameterRequest.getUserId())).thenReturn(cookies);

        List<Cookie> resultCookieList = cookieService.getByParam(cookiesByParameterRequest.getName(),
                cookiesByParameterRequest.getDescription(), cookiesByParameterRequest.getCookieAddingStatus(),
                cookiesByParameterRequest.getRating(), cookiesByParameterRequest.getUserId());

        Assert.assertEquals(resultCookieList.get(0).getCookieId(), cookieWithId.getCookieId());
        Assert.assertEquals(resultCookieList.get(0).getName(), cookieWithId.getName());
        Assert.assertEquals(resultCookieList.get(0).getDescription(), cookieWithId.getDescription());
        Assert.assertArrayEquals(resultCookieList.get(0).getFileData(), cookieWithId.getFileData());
        Assert.assertEquals(resultCookieList.get(0).getCookieAddingStatus(),
                            cookieWithId.getCookieAddingStatus());
        Assert.assertEquals(resultCookieList.get(0).getRating(), cookieWithId.getRating());
        Assert.assertEquals(resultCookieList.get(0).getCookieOwner(), cookieWithId.getCookieOwner());
        Assert.assertEquals(resultCookieList.get(0).getUsersRatings(), cookieWithId.getUsersRatings());

        Assert.assertEquals(resultCookieList.get(1).getCookieId(), cookieWith2Id.getCookieId());
        Assert.assertEquals(resultCookieList.get(1).getName(), cookieWith2Id.getName());
        Assert.assertEquals(resultCookieList.get(1).getDescription(), cookieWith2Id.getDescription());
        Assert.assertArrayEquals(resultCookieList.get(1).getFileData(), cookieWith2Id.getFileData());
        Assert.assertEquals(resultCookieList.get(1).getCookieAddingStatus(),
                                    cookieWith2Id.getCookieAddingStatus());
        Assert.assertEquals(resultCookieList.get(1).getRating(), cookieWith2Id.getRating());
        Assert.assertEquals(resultCookieList.get(1).getCookieOwner(), cookieWith2Id.getCookieOwner());
        Assert.assertEquals(resultCookieList.get(1).getUsersRatings(), cookieWith2Id.getUsersRatings());

        verify(cookieDao).getByParam(cookiesByParameterRequest.getName(), cookiesByParameterRequest.getDescription(),
                cookiesByParameterRequest.getCookieAddingStatus(), cookiesByParameterRequest.getRating(),
                cookiesByParameterRequest.getUserId());
    }

    @Test
    public void testUpdateCookie () {
        Cookie cookie = new Cookie(1, "cookie", "tasty cookie", new byte[2],
                CookieAddingStatus.WAITING, cookieRating, cookieOwner);

        when(cookieDao.update(cookieWithId)).thenReturn(cookieWithId);

        cookieService.update(cookie);

        verify(cookieDao).update(cookieWithId);
    }

    @Test
    public void testGetById () {
        cookieWithId.setUsersRatings(usersRatings);

        when(cookieDao.getById(1)).thenReturn(cookieWithId);

        Cookie resultCookie = cookieService.getById(1);

        Assert.assertEquals(resultCookie.getCookieId(), cookieWithId.getCookieId());
        Assert.assertEquals(resultCookie.getName(), cookieWithId.getName());
        Assert.assertEquals(resultCookie.getDescription(), cookieWithId.getDescription());
        Assert.assertArrayEquals(resultCookie.getFileData(), cookieWithId.getFileData());
        Assert.assertEquals(resultCookie.getRating(), cookieWithId.getRating());
        Assert.assertEquals(resultCookie.getCookieOwner(), cookieWithId.getCookieOwner());
        Assert.assertEquals(resultCookie.getUsersRatings(), cookieWithId.getUsersRatings());

        verify(cookieDao).getById(1);
    }

    @Test
    public void testGetUnratedCookiesByUserId () {
        List<Cookie> cookies = Arrays.asList(cookieWithId, cookieWith2Id);

        when(cookieDao.getUnratedByUserId(userId)).thenReturn(cookies);

        List<Cookie> resultCookieList = cookieService.getUnratedByUserId(userId);

        Assert.assertEquals(resultCookieList.get(0).getCookieId(), cookieWithId.getCookieId());
        Assert.assertEquals(resultCookieList.get(0).getName(), cookieWithId.getName());
        Assert.assertEquals(resultCookieList.get(0).getDescription(), cookieWithId.getDescription());
        Assert.assertArrayEquals(resultCookieList.get(0).getFileData(), cookieWithId.getFileData());
        Assert.assertEquals(resultCookieList.get(0).getCookieAddingStatus(),
                cookieWithId.getCookieAddingStatus());
        Assert.assertEquals(resultCookieList.get(0).getRating(), cookieWithId.getRating());

        Assert.assertEquals(resultCookieList.get(1).getCookieId(), cookieWith2Id.getCookieId());
        Assert.assertEquals(resultCookieList.get(1).getName(), cookieWith2Id.getName());
        Assert.assertEquals(resultCookieList.get(1).getDescription(), cookieWith2Id.getDescription());
        Assert.assertArrayEquals(resultCookieList.get(1).getFileData(), cookieWith2Id.getFileData());
        Assert.assertEquals(resultCookieList.get(1).getCookieAddingStatus(),
                cookieWith2Id.getCookieAddingStatus());
        Assert.assertEquals(resultCookieList.get(1).getRating(), cookieWith2Id.getRating());

        verify(cookieDao).getUnratedByUserId(userId);
    }

    @Test
    public void testCountRating () {
        float rating = (float) 3;
        cookieWithId.setUsersRatings(usersRatings);

        when(cookieDao.getById(1)).thenReturn(cookieWithId);

        float resultRating = cookieService.countRating(cookieWithId);

        Assert.assertEquals(rating, resultRating, 0.0f);
    }


}

