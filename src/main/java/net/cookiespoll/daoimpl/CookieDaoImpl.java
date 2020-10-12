package net.cookiespoll.daoimpl;

import net.cookiespoll.dao.CookieDao;
import net.cookiespoll.mapper.CookieMapper;
import net.cookiespoll.model.Cookie;
import net.cookiespoll.model.CookieAddingStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class CookieDaoImpl implements CookieDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(CookieDaoImpl.class);

    private final CookieMapper cookieMapper;

    @Autowired
    public CookieDaoImpl(CookieMapper cookieMapper) {
        this.cookieMapper = cookieMapper;
    }

    @Override
    @Transactional
    public Cookie insert(Cookie cookie) {
        LOGGER.info("Adding cookie to database {} ", cookie);

        cookie.setCookieId(cookieMapper.insert(cookie));
        return cookie;
    }

    @Override
    public Cookie getById(Integer id) {
        LOGGER.info("Find cookie by id in database {} ", id);

        return cookieMapper.getById(id);
    }

    @Override
    public List<Cookie> getByParam(String name, String description, CookieAddingStatus cookieAddingStatus,
                                   Float rating, String userId) {
        LOGGER.info("Extract list of cookies by given parameters {} {} {} {} {} ", name,
                description, cookieAddingStatus, rating, userId);

        return cookieMapper.getByParam(name, description, cookieAddingStatus, rating, userId);
    }

    @Override
    public List<Cookie> getUnratedByUserId(String userId) {
        return cookieMapper.getUnratedByUserId(userId);
    }

    @Override
    public Cookie update(Cookie cookie) {
        LOGGER.info("Update cookie {} ", cookie);

        cookieMapper.update(cookie);
        return cookie;
    }

    @Override
    public Integer delete (Integer id) {
        return cookieMapper.delete(id);
    }
}
