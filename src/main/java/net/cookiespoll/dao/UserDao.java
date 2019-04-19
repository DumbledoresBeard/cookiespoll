package net.cookiespoll.dao;

import net.cookiespoll.model.Cookie;
import net.cookiespoll.model.User;

public interface UserDao {

    public User insert (User user);

    public User getUserById (int id);

    public void updateUser (User user);

    public void deleteUser (User user);
}
