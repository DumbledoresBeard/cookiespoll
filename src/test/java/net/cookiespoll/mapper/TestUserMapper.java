package net.cookiespoll.mapper;

import net.cookiespoll.user.Role;
import net.cookiespoll.user.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TestUserMapper {

    @Mock
    UserMapper userMapper;

    User userAdmin = new User (1, "login", "password", "name", "lastname",
            Role.ADMIN);
    int id = 1;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testUserMapperGetUserById () {
        when(userMapper.getUserById(id)).thenReturn(userAdmin);
        userMapper.getUserById(id);
        verify(userMapper).getUserById(id);
    }

}
