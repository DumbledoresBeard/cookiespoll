/*
package net.cookiespoll.service;

import net.cookiespoll.dao.CookieUserRatingDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TestCookieUserRatingService {

    @Mock
    private CookieUserRatingDao cookieUserRatingDao;

    @InjectMocks
    private CookieUserRatingService cookieUserRatingService;

    Integer cookieId = 1;
    String userId = "1";
    Integer rating = 1;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testGetRatingByUserAndCookie () {
        when(cookieUserRatingDao.getRatingByUserAndCookie(userId, cookieId)).thenReturn(rating);

        assert rating == cookieUserRatingService.getRatingByUserAndCookie(userId, cookieId);

        verify(cookieUserRatingDao).getRatingByUserAndCookie(userId, cookieId);
    }

    @Test
    public void testGetUserQuantity () {
        Integer quantity = 10;
        when(cookieUserRatingDao.getUserQuantity(cookieId)).thenReturn(quantity);

        assert quantity == cookieUserRatingService.getUserQuantity(cookieId);

        verify(cookieUserRatingDao).getUserQuantity(cookieId);
    }

    @Test
    public void testGetRatingSumByCookieId () {
        List<Integer> ratings = Arrays.asList(1, 2, 3, 4, 5);
        Float sum = Float.valueOf(15);

        when(cookieUserRatingDao.getRatingsByCookieId(cookieId)).thenReturn(ratings);

        Float resultSum = cookieUserRatingService.getRatingSumByCookieId(cookieId);

        Assert.assertEquals(sum, resultSum);

        verify(cookieUserRatingDao).getRatingsByCookieId(cookieId);

    }
}
*/
