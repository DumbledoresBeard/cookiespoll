package net.cookiespoll.daoimpl;

import net.cookiespoll.dao.CookieDao;
import net.cookiespoll.mapper.CookieMapper;
import net.cookiespoll.model.Cookie;
import net.cookiespoll.model.CookieAddingStatus;
import net.cookiespoll.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        LOGGER.info("Adding cookie to database {} ", cookie);
        cookie.setId(cookieMapper.insert(cookie, cookie.getCookieOwner().getId()));
        return cookie;
    }

    @Override
    public Cookie getById(Integer id) {
        LOGGER.info("Find cookie by id in database {} ", id);
        return cookieMapper.getById(id);
    }

    @Override
    public List<Cookie> getByParam(String name, String description, CookieAddingStatus cookieAddingStatus,
                                   Integer rating, Integer userId) {
        LOGGER.info("Extract list of cookies by given parameters {} ", cookieAddingStatus);
        return cookieMapper.getByParam(name, description, cookieAddingStatus, rating, userId);
    }

    @Override
    public Cookie update(Cookie cookie) {
        LOGGER.info("Update cookie {} ", cookie);
        cookieMapper.update(cookie, cookie.getCookieOwner().getId());
        return cookie;
    }

    @Override
    public void delete (Cookie cookie) {

    }
}
