package net.cookiespoll.dao;

import net.cookiespoll.model.Cookie;
import net.cookiespoll.model.CookieAddingStatus;

import java.util.List;


public interface CookieDao {

    Cookie insert (Cookie cookie);

    Cookie getById(Integer id);

    List<Cookie> getByParam(String name, String description,
                                   CookieAddingStatus cookieAddingStatus, Float rating, Integer userId);
    public List<Cookie> getUnratedCookiesByUserId (int userId);

    Cookie update(Cookie cookie);

    void delete (Cookie cookie);
}
