package net.cookiespoll.daoimpl;

import net.cookiespoll.dao.UserDao;
import net.cookiespoll.mapper.UserMapper;
import net.cookiespoll.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
    public User getById(int id) {
        User user = userMapper.getUserById(id);
        return user;
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
