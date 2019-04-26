package net.cookiespoll.dao;

import net.cookiespoll.model.Cookie;
import net.cookiespoll.model.CookieAddingStatus;

import java.util.List;


public interface CookieDao {

    public Cookie insert (Cookie cookie);

    public Cookie getById(int id);

    public List<Cookie> getByParam(Integer id, String name, String description,
                                   CookieAddingStatus cookieAddingStatus, Integer rating, Integer userId);

    public Cookie update(Cookie cookie);

    public void delete (Cookie cookie);
}
