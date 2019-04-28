package net.cookiespoll.daoimpl;

import net.cookiespoll.dao.CookieUserRatingDao;
import net.cookiespoll.mapper.CookieUserRatingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CookieUserRatingDaoImpl implements CookieUserRatingDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(CookieUserRatingDaoImpl.class);

    private CookieUserRatingMapper cookieUserRatingMapper;

    @Autowired
    public CookieUserRatingDaoImpl(CookieUserRatingMapper cookieUserRatingMapper) {
        this.cookieUserRatingMapper = cookieUserRatingMapper;
    }


    @Override
    public int insert(int userId, int cookieId, int rating) {
        LOGGER.info("Adding cookie rating in database {} {} {}", userId, cookieId, rating);

        cookieUserRatingMapper.insert(userId, cookieId, rating);
        return 0;
    }

    @Override
    public Integer getRatingByUserAndCookie(int userId, int cookieId) {
        LOGGER.info("Select cookie rating by cookieId and userId", userId, cookieId);

        return cookieUserRatingMapper.getRatingByUserAndCookie(userId, cookieId);
    }

}
