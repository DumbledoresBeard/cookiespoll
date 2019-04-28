package net.cookiespoll.service;

import net.cookiespoll.dao.CookieUserRatingDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CookieUserRatingService {

    private CookieUserRatingDao cookieUserRatingDao;

    @Autowired
    public CookieUserRatingService(CookieUserRatingDao cookieUserRatingDao) {
        this.cookieUserRatingDao = cookieUserRatingDao;
    }

    public int setRatingToCookie(int userId, int cookieId, int rating) {
        return cookieUserRatingDao.insert(userId, cookieId, rating);
    }

    public Integer getRatingByUserAndCookie(int userId, int cookieId) {
        return cookieUserRatingDao.getRatingByUserAndCookie(userId, cookieId);
    }
    }

