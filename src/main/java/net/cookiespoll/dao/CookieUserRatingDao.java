package net.cookiespoll.dao;


import java.util.List;

public interface CookieUserRatingDao {

    void insert (String userId, Integer cookieId, Integer rating);


    Integer getRatingByUserAndCookie(String userId, Integer cookieId);

    Integer getUserQuantity (Integer cookieId);

    List<Integer> getRatingsByCookieId(Integer cookieId);


}
