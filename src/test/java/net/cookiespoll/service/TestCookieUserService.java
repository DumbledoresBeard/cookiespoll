package net.cookiespoll.service;

import net.cookiespoll.dao.CookieUserRatingDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TestCookieUserService {

    @Mock
    private CookieUserRatingDao cookieUserRatingDao;

    @InjectMocks
    private CookieUserRatingService cookieUserRatingService;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testGetRatingByUserAndCookie () {

    }
}
