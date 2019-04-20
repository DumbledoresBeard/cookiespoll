package net.cookiespoll.dao;

import net.cookiespoll.model.Cookie;
import net.cookiespoll.model.CookieAddingStatus;

import java.util.List;


public interface CookieDao {

    public Cookie insert (Cookie cookie);

    public Cookie getCookieById (int id);

    public List<Cookie> getCookieListByCookieAddingStatus(CookieAddingStatus cookieAddingStatus);

    public void updateCookie (Cookie cookie);

    public void deleteCookie (Cookie cookie);
}
