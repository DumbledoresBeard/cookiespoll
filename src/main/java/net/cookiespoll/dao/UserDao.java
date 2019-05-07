package net.cookiespoll.dao;

import net.cookiespoll.model.User;

public interface UserDao {

    User insert (User user);

    User getById(int id);

    User update (User user);

}
