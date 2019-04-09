package net.cookiespoll.mapper;

import net.cookiespoll.model.Cookie;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TestCookieMapper {

    @Mock
    CookieMapper cookieMapper;

    Cookie cookie = new Cookie("cookie", "tasty cookie", new byte[2]);

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testCookieMapperInsert() {
        when(cookieMapper.insert(cookie)).thenReturn(1);
        Assert.assertEquals(cookieMapper.insert(cookie), new Integer(1));

    }
}
