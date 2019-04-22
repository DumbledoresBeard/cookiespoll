package net.cookiespoll.daoimpl;

import net.cookiespoll.dao.CookieDao;
import net.cookiespoll.mapper.CookieMapper;
import net.cookiespoll.model.Cookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CookieDaoImpl implements CookieDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(CookieDaoImpl.class);

    private CookieMapper cookieMapper;

    @Autowired
    public CookieDaoImpl(CookieMapper cookieMapper) {
        this.cookieMapper = cookieMapper;
    }

    @Override
    @Transactional
    public Cookie insert(Cookie cookie) {
        LOGGER.info("Adding cookie to database ", cookie);
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
