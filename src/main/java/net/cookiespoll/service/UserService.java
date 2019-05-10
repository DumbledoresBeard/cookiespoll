package net.cookiespoll.service;

import net.cookiespoll.dao.UserDao;
import net.cookiespoll.model.user.Role;
import net.cookiespoll.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public Role getRole (int id) {
        User user = userDao.getById(id);
        return user.getRole();
    }
}
