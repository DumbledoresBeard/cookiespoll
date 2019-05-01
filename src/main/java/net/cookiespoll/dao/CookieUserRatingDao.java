package net.cookiespoll.dao;


import java.util.List;

public interface CookieUserRatingDao {

    Integer insert (Integer userId, Integer cookieId, Integer rating);

    Integer getRatingByUserAndCookie(Integer userId, Integer cookieId);

    Integer getUserQuantity (Integer cookieId);

    List<Integer> getRatingsByCookieId(Integer cookieId);


}
