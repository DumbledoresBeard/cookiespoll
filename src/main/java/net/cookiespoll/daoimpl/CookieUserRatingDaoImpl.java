package net.cookiespoll.daoimpl;

import net.cookiespoll.dao.CookieUserRatingDao;
import net.cookiespoll.mapper.CookieUserRatingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CookieUserRatingDaoImpl implements CookieUserRatingDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(CookieUserRatingDaoImpl.class);

    private CookieUserRatingMapper cookieUserRatingMapper;

    @Autowired
    public CookieUserRatingDaoImpl(CookieUserRatingMapper cookieUserRatingMapper) {
        this.cookieUserRatingMapper = cookieUserRatingMapper;
    }

    @Override
    public Integer insert(String userId, Integer cookieId, Integer rating) {
        LOGGER.info("Adding cookie rating in database {} {} {}", userId, cookieId, rating);

        return cookieUserRatingMapper.insert(userId, cookieId, rating);
    }

    @Override
    public Integer getRatingByUserAndCookie(String userId, Integer cookieId) {
        LOGGER.info("Select cookie rating by cookieId and userId", userId, cookieId);

        return cookieUserRatingMapper.getRatingByUserAndCookie(userId, cookieId);
    }

    @Override
    public Integer getUserQuantity(Integer cookieId) {
        return cookieUserRatingMapper.getUserQuantity(cookieId);
    }

    @Override
    public List<Integer> getRatingsByCookieId(Integer cookieId) {
        return cookieUserRatingMapper.getRatingByCookieId(cookieId);
    }
}
