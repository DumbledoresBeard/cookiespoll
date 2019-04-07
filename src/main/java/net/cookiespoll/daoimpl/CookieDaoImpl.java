package net.cookiespoll.daoimpl;

import net.cookiespoll.dao.CookieDao;
import net.cookiespoll.mappers.CookieMapper;
import net.cookiespoll.model.Cookie;
import org.springframework.beans.factory.annotation.Autowired;

public class CookieDaoImpl implements CookieDao {

    @Autowired
    private CookieMapper cookieMapper;

    @Override
    public Cookie insert(Cookie cookie) {
        cookieMapper.insert(cookie);
        return cookie;
    }

    @Override
    public Cookie getCookieById(int id) {
        return null;
    }

    @Override
    public void updateCookie(Cookie cookie) {

    }

    @Override
    public void deleteCookie(Cookie cookie) {

    }
}
