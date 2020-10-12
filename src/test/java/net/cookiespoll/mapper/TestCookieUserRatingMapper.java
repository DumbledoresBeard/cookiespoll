
package net.cookiespoll.mapper;

import net.cookiespoll.model.Cookie;
import net.cookiespoll.model.CookieAddingStatus;
import net.cookiespoll.model.CookieUserRating;
import net.cookiespoll.model.user.Role;
import net.cookiespoll.model.user.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TestCookieUserRatingMapper {

    @Mock
    private CookieUserRatingMapper cookieUserRatingMapper;

    private Integer rating = 1;
    private String userId = "1";
    private Integer cookieId = 1;
    private User cookieOwner = new User("1", "login", "name", Role.USER);
    private Cookie cookieWith1Id = new Cookie(1, "cookie", "tasty cookie",
            new byte[2], CookieAddingStatus.APPROVED, (float) 0, cookieOwner);
    private List<CookieUserRating> usersRatings =
            Collections.singletonList(new CookieUserRating(cookieOwner, cookieWith1Id, 3));

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetRatingByUserAndCookie() {
        when(cookieUserRatingMapper.getRatingByUserAndCookie(userId, cookieId)).thenReturn(rating);

        assert rating.equals(cookieUserRatingMapper.getRatingByUserAndCookie(userId, cookieId));

        verify(cookieUserRatingMapper).getRatingByUserAndCookie(userId, cookieId);
    }

    @Test
    public void testGetUserQuantity() {
        when(cookieUserRatingMapper.getUserQuantity(cookieId)).thenReturn(5);

        assert 5 == cookieUserRatingMapper.getUserQuantity(cookieId);

        verify(cookieUserRatingMapper).getUserQuantity(cookieId);
    }

    @Test
    public void testGetRatingByCookieId() {
        List<Integer> ratings = Arrays.asList(1, 2, 3, 4, 5);
        when(cookieUserRatingMapper.getRatingByCookieId(cookieId)).thenReturn(ratings);

        Assert.assertEquals(ratings, cookieUserRatingMapper.getRatingByCookieId(cookieId));

        verify(cookieUserRatingMapper).getRatingByCookieId(cookieId);
    }

    @Test
    public void testGetListByUserId() {
        when(cookieUserRatingMapper.getListByUserId(userId)).thenReturn(usersRatings);

        List<CookieUserRating> result = cookieUserRatingMapper.getListByUserId(userId);

        Assert.assertEquals(usersRatings.get(0).getCookie(), result.get(0).getCookie());
        Assert.assertEquals(usersRatings.get(0).getUser(), result.get(0).getUser());
        assert usersRatings.get(0).getRating().equals(result.get(0).getRating());

        verify(cookieUserRatingMapper).getListByUserId(userId);

    }

    @Test
    public void testGetListByCookieId() {
        when(cookieUserRatingMapper.getListByCookieId(cookieId)).thenReturn(usersRatings);

        List<CookieUserRating> result = cookieUserRatingMapper.getListByCookieId(cookieId);

        Assert.assertEquals(usersRatings.get(0).getCookie(), result.get(0).getCookie());
        Assert.assertEquals(usersRatings.get(0).getUser(), result.get(0).getUser());
        assert usersRatings.get(0).getRating().equals(result.get(0).getRating());

        verify(cookieUserRatingMapper).getListByCookieId(cookieId);

    }
}

