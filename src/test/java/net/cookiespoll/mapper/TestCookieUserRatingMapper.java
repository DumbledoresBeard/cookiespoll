package net.cookiespoll.mapper;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TestCookieUserRatingMapper {

    @Mock
    private CookieUserRatingMapper cookieUserRatingMapper;

    private Integer rating = 1;
    private Integer userId = 1;
    private Integer cookieId = 1;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetRatingByUserAndCookie () {
        when(cookieUserRatingMapper.getRatingByUserAndCookie(userId, cookieId)).thenReturn(rating);

        assert  rating == cookieUserRatingMapper.getRatingByUserAndCookie(userId, cookieId);

        verify(cookieUserRatingMapper).getRatingByUserAndCookie(userId, cookieId);
    }

    @Test
    public void testGetUserQuantity () {
        when(cookieUserRatingMapper.getUserQuantity(cookieId)).thenReturn(5);

        assert 5 == cookieUserRatingMapper.getUserQuantity(cookieId);

        verify(cookieUserRatingMapper).getUserQuantity(cookieId);
    }

    @Test
    public void testGetRatingByCookieId () {
        List<Integer> ratings = Arrays.asList(1, 2, 3, 4, 5);
        when(cookieUserRatingMapper.getRatingByCookieId(cookieId)).thenReturn(ratings);

        Assert.assertEquals(ratings, cookieUserRatingMapper.getRatingByCookieId(cookieId));

        verify(cookieUserRatingMapper).getRatingByCookieId(cookieId);
    }
}
