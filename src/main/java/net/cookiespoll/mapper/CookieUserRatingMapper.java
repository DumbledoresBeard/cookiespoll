package net.cookiespoll.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface CookieUserRatingMapper {

    @Insert("INSERT INTO cookie_user_rating (user_id, cookie_id, rating) VALUES "
            + "( #{userId}, #(cookieId), #(rating))")
    Integer insert(@Param("userId") int user_id, @Param("cookieId") int cookie_id, @Param("rating") int rating);

}
