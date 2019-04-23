package net.cookiespoll.dao;

import net.cookiespoll.daoimpl.UserDaoImpl;
import net.cookiespoll.mapper.UserMapper;
import net.cookiespoll.user.User;
import net.cookiespoll.user.Role;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TestUserDaoImpl {

    @Mock
    UserMapper userMapper;
    @InjectMocks
    UserDaoImpl userDaoImpl;

    int id = 1;
    User userAdmin = new User(1, "login", "password", "name", "lastname",
            Role.ADMIN);

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testUserDaoGetUserById () {
        when(userMapper.getUserById(id)).thenReturn(userAdmin);
        User resultUser = userDaoImpl.getById(id);

        Assert.assertEquals(userAdmin.getId(), resultUser.getId());
        Assert.assertEquals(userAdmin.getLogin(), resultUser.getLogin());
        Assert.assertEquals(userAdmin.getPassword(), resultUser.getPassword());
        Assert.assertEquals(userAdmin.getFirstName(), resultUser.getFirstName());
        Assert.assertEquals(userAdmin.getLastName(), resultUser.getLastName());
        Assert.assertEquals(userAdmin.getRole(), resultUser.getRole());

        verify(userMapper).getUserById(id);
    }

}
