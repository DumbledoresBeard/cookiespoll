package net.cookiespoll.service;

import net.cookiespoll.daoimpl.UserDaoImpl;
import net.cookiespoll.user.Role;
import net.cookiespoll.user.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TestUserService {
    @Mock
    UserDaoImpl userDao;

    @InjectMocks
    UserService userService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testGetUserRole() {
        int id = 1;
        User userAdmin = new User (1, "login", "password", "name", "lastname",
                Role.ADMIN);
        Role admin = Role.ADMIN;

        when(userDao.getById(id)).thenReturn(userAdmin);

        Role resultRole = userService.getRole(id);

        Assert.assertEquals(admin, resultRole);

        verify(userDao).getById(id);

    }

}
