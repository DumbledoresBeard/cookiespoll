package net.cookiespoll.dao;

import net.cookiespoll.model.Cookie;

public interface CookieDao {

    public Cookie insert (Cookie cookie);

    public Cookie getCookieById (int id);

    public void updateCookie (Cookie cookie);

    public void deleteCookie (Cookie cookie);
}
