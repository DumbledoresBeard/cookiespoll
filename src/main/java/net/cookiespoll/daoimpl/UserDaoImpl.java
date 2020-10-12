package net.cookiespoll.daoimpl;

import net.cookiespoll.dao.UserDao;
import net.cookiespoll.mapper.UserMapper;
import net.cookiespoll.model.user.Admin;
import net.cookiespoll.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDaoImpl implements UserDao {

    private final UserMapper userMapper;

    @Autowired
    public UserDaoImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User insert(User user) {
        userMapper.insert(user);
        return user;
    }

    @Override
    public User getById(String id) {
        return userMapper.getById(id);
    }

    @Override
    public User update(User user) {
        userMapper.update(user);
        return user;
    }

    @Override
    public List<Admin> getAdmins() {
        return userMapper.getAdmins();
    }

}
