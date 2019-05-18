/*package net.cookiespoll.service;

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

    public void setRatingToCookie(Integer userId, Integer cookieId, Integer rating) {
        cookieUserRatingDao.insert(userId, cookieId, rating);
    }

    public Integer getRatingByUserAndCookie(Integer userId, Integer cookieId) {
        return cookieUserRatingDao.getRatingByUserAndCookie(userId, cookieId);
    }

    public Integer getUserQuantity (Integer cookieId) {
        return cookieUserRatingDao.getUserQuantity(cookieId);
    }

    public Float getRatingSumByCookieId(Integer cookieId) {
        return (float) cookieUserRatingDao.getRatingsByCookieId(cookieId)
                .stream()
                .mapToInt((x) -> x)
                .summaryStatistics()
                .getSum();
    }
    }*/

