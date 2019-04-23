package net.cookiespoll.dao;

import net.cookiespoll.user.User;

public interface UserDao {

    public User insert (User user);

    public User getById(int id);

    public User update (User user);

    public void delete (User user);
}
