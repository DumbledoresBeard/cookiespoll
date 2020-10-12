package net.cookiespoll.mapper;

import net.cookiespoll.model.user.Admin;
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

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TestUserMapper {

    @Mock
    UserMapper userMapper;

    private User userAdmin = new User ("1", "login", "name", Role.ADMIN);

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetUserById() {
        String id = "1";
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

    @Test
    public void testGetAdmins() {
        List<Admin> admins = Collections.singletonList(new Admin(1, "some@mail.com"));
        when(userMapper.getAdmins()).thenReturn(admins);

        List<Admin> resultAdmins = userMapper.getAdmins();

        Assert.assertEquals(admins.get(0).getId(), resultAdmins.get(0).getId());

        verify(userMapper).getAdmins();
    }

}
