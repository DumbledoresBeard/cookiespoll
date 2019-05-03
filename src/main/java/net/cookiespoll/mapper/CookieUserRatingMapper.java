package net.cookiespoll.mapper;

import org.apache.ibatis.annotations.*;

import java.util.List;

public interface CookieUserRatingMapper {

    @Insert("INSERT INTO cookie_user_rating (user_id, cookie_id, rating) VALUES "
            + "( #{userId}, #{cookieId}, #{rating})")
    Integer insert(@Param("userId") String userId, @Param("cookieId") int cookieId, @Param("rating") int rating);

    @Select("SELECT rating from cookie_user_rating where user_id =#{userId} AND cookie_id=#{cookieId}")
    Integer getRatingByUserAndCookie(@Param("userId") String userId, @Param("cookieId") int cookieId);

    @Select("SELECT COUNT(user_id) WHERE cookie_id = #{cookieId}")
    Integer getUserQuantity (@Param("cookieId") Integer cookieId);

    @Select("SELECT rating from cookie_user_rating where cookie_id=#{cookieId}")
    List<Integer> getRatingByCookieId(@Param("cookieId") Integer cookieId);



}
