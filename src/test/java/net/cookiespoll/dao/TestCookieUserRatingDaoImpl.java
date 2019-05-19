/*package net.cookiespoll.dao;

import net.cookiespoll.daoimpl.CookieUserRatingDaoImpl;
import net.cookiespoll.mapper.CookieUserRatingMapper;
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
public class TestCookieUserRatingDaoImpl {

    @Mock
    private CookieUserRatingMapper cookieUserRatingMapper;

    @InjectMocks
    private CookieUserRatingDaoImpl cookieUserRatingDao;

    private Integer rating = 1;
    private String userId = "1";
    private Integer cookieId = 1;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetRatingByUserAndCookie () {
        when(cookieUserRatingMapper.getRatingByUserAndCookie(userId, cookieId)).thenReturn(rating);

        assert rating == cookieUserRatingDao.getRatingByUserAndCookie(userId, cookieId);

        verify(cookieUserRatingMapper).getRatingByUserAndCookie(userId, cookieId);
    }

    @Test
    public void testGetUserQuantity () {
        when(cookieUserRatingMapper.getUserQuantity(cookieId)).thenReturn(5);

        assert 5 == cookieUserRatingDao.getUserQuantity(cookieId);

        verify(cookieUserRatingMapper).getUserQuantity(cookieId);
    }

    @Test
    public void testGetRatingsByCookieId () {
        List<Integer> ratings = Arrays.asList(1, 2, 3, 4, 5);

        when(cookieUserRatingMapper.getRatingByCookieId(cookieId)).thenReturn(ratings);

        Assert.assertEquals(ratings, cookieUserRatingDao.getRatingsByCookieId(cookieId));

        verify(cookieUserRatingMapper).getRatingByCookieId(cookieId);
    }
}*/
