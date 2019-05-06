package net.cookiespoll.dao;

import net.cookiespoll.model.Cookie;
import net.cookiespoll.model.CookieAddingStatus;
import net.cookiespoll.user.User;

import java.util.List;


public interface CookieDao {

    Cookie insert(Cookie cookie);

    Cookie getById(Integer id);

    List<Cookie> getByParam(String name, String description,
                                   CookieAddingStatus cookieAddingStatus, Integer rating, Integer userId);

    Cookie update(Cookie cookie);

    void delete (Cookie cookie);
}
