/*package net.cookiespoll.mapper;

import net.cookiespoll.model.Cookie;
import net.cookiespoll.model.CookieAddingStatus;
import net.cookiespoll.model.CookieUserRating;
import net.cookiespoll.model.user.Role;
import net.cookiespoll.model.user.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface CookieUserRatingMapper {

    @Insert("INSERT INTO cookie_user_rating (user_id, cookie_id, rating) VALUES "
            + "( #{userId}, #{cookieId}, #{rating})")
    void insert(@Param("userId") String userId, @Param("cookieId") int cookieId, @Param("rating") int rating);


    @Select("SELECT rating from cookie_user_rating where user_id =#{userId} AND cookie_id=#{cookieId}")
    Integer getRatingByUserAndCookie(@Param("userId") String userId, @Param("cookieId") int cookieId);

    @Select("SELECT COUNT(user_id) WHERE cookie_id = #{cookieId}")
    Integer getUserQuantity (@Param("cookieId") Integer cookieId);

    @Select("SELECT rating from cookie_user_rating where cookie_id=#{cookieId}")
    List<Integer> getRatingByCookieId(@Param("cookieId") Integer cookieId);

    @Select("SELECT user_id, cookie_id, rating from cookie_user_rating " +
            "WHERE user_id = #{userId}")
    @Results({
            @Result(property = "user", column = "user_id", javaType = User.class,
                    one = @One(select = "net.cookiespoll.mapper.UserMapper.getById")),
            @Result(property = "cookie", column = "cookie_id", javaType = Cookie.class,
                    one = @One(select = "net.cookiespoll.mapper.CookieMapper.getById")),
            @Result(property = "rating", column = "rating", javaType = Integer.class),
    })
    List<CookieUserRating> getListByUserId(Integer userId);

    @Select("SELECT user_id, cookie_id, rating from cookie_user_rating " +
            "WHERE cookie_id = #{cookieId}")
    @Results({
            @Result(property = "user", column = "user_id", javaType = User.class,
                    one = @One(select = "net.cookiespoll.mapper.UserMapper.getById")),
            @Result(property = "cookie", column = "cookie_id", javaType = Cookie.class,
                    one = @One(select = "net.cookiespoll.mapper.CookieMapper.getById")),
            @Result(property = "rating", column = "rating", javaType = Integer.class),
    })
    List<CookieUserRating> getListByCookieId(Integer cookieId);
}*/
