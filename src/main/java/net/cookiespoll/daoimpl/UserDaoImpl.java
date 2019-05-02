package net.cookiespoll.daoimpl;

import net.cookiespoll.dao.UserDao;
import net.cookiespoll.mapper.UserMapper;
import net.cookiespoll.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public class UserDaoImpl implements UserDao {

    private UserMapper userMapper;

    @Autowired
    public UserDaoImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User insert(User user) {
        /*TODO*/
        return new User();
    }

    @Override
    @Transactional
    public User getById(String id) {
        return userMapper.getUserById(id);
    }

    @Override
    public User update(User user) {
        /*TODO*/
        return new User();
    }

    @Override
    public void delete(User user) {

    }
}
