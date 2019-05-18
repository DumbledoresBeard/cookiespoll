
package net.cookiespoll.mapper;

import net.cookiespoll.model.Cookie;
import net.cookiespoll.model.CookieAddingStatus;
import net.cookiespoll.model.CookieUserRating;
import net.cookiespoll.model.user.Role;
import net.cookiespoll.model.user.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TestUserMapper {

    @Mock
    UserMapper userMapper;

    private User userAdmin = new User (1, "login", "name", Role.ADMIN);
    private int id = 1;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testUserMapperGetUserById() {
        when(userMapper.getById(id)).thenReturn(userAdmin);

        userMapper.getById(id);

        verify(userMapper).getById(id);
    }

    @Test
    public void testUpdateUser() {
        doNothing().when(userMapper).update(userAdmin);

        userMapper.update(userAdmin);

        verify(userMapper).update(userAdmin);
    }

}
