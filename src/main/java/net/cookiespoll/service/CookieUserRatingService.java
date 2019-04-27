package net.cookiespoll.service;

import net.cookiespoll.dao.CookieUserRatingDao;
import org.springframework.beans.factory.annotation.Autowired;

public class CookieUserRatingService {

    private CookieUserRatingDao cookieUserRatingDao;

    @Autowired
    public CookieUserRatingService(CookieUserRatingDao cookieUserRatingDao) {
        this.cookieUserRatingDao = cookieUserRatingDao;
    }

    public int addCookieToAllUsers (int cookie_id) {
        int rating = 0;
        return cookieUserRatingDao.updateAddCookieToAllUsers(cookie_id, rating);
    }
}
