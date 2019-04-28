package net.cookiespoll.mapper;

import org.apache.ibatis.annotations.*;

public interface CookieUserRatingMapper {

    @Insert("INSERT INTO cookie_user_rating (user_id, cookie_id, rating) VALUES "
            + "( #{userId}, #{cookieId}, #{rating})")
    Integer insert(@Param("userId") int userId, @Param("cookieId") int cookieId, @Param("rating") int rating);

    @Select("SELECT rating from cookie_user_rating where user_id =#{userId} AND cookie_id=#{cookieId}")
    Integer getRatingByUserAndCookie(@Param("userId") int userId, @Param("cookieId") int cookieId);


}
