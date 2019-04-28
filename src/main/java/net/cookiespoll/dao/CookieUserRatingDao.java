package net.cookiespoll.dao;

public interface CookieUserRatingDao {

    public int insert (int userId, int cookieId, int rating);

    public Integer getRatingByUserAndCookie(int userId, int cookieId);

}
