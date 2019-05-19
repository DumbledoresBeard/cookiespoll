package net.cookiespoll.dao;

import net.cookiespoll.model.CookieUserRating;
import net.cookiespoll.model.user.Admin;
import net.cookiespoll.model.user.User;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public interface UserDao {

    User insert (User user);

    User getById(String id);

    User update(User user);

    List<Admin> getAdmins();

}
