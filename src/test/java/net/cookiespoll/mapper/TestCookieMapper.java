package net.cookiespoll.mapper;

import net.cookiespoll.model.Cookie;
import net.cookiespoll.model.CookieAddingStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TestCookieMapper {

    @Mock
    CookieMapper cookieMapper;

    Cookie cookie = new Cookie("cookie", "tasty cookie", new byte[2], CookieAddingStatus.WAITING,
                                0, 1);

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testCookieMapperInsert() {
        when(cookieMapper.insert(cookie)).thenReturn(1);
        assert cookieMapper.insert(cookie) == 1;
        verify(cookieMapper).insert(cookie);

    }

    @Test
    public void testCookieMapperUpdate () {
        doNothing().when(cookieMapper).update(cookie);
        verify(cookieMapper).update(cookie);
    }
}
