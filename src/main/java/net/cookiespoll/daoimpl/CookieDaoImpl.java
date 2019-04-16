package net.cookiespoll.daoimpl;

import net.cookiespoll.dao.CookieDao;
import net.cookiespoll.mapper.CookieMapper;
import net.cookiespoll.model.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class CookieDaoImpl implements CookieDao {
    @Autowired
    private CookieMapper cookieMapper;

    @Override
    @Transactional
    public Cookie insert(Cookie cookie) {
        int id = cookieMapper.insert(cookie);
        cookie.setId(id);
        return cookie;
    }

    @Override
    public Cookie getCookieById(int id) {
        /*To Do*/ return new Cookie();
    }

    @Override
    public void updateCookie(Cookie cookie) {

    }

    @Override
    public void deleteCookie(Cookie cookie) {

    }
}
