package net.cookiespoll.mapper;

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
    void insert(@Param("userId") int userId, @Param("cookieId") int cookieId, @Param("rating") int rating);

    @Select("SELECT rating from cookie_user_rating where user_id =#{userId} AND cookie_id=#{cookieId}")
    Integer getRatingByUserAndCookie(@Param("userId") int userId, @Param("cookieId") int cookieId);

    @Select("SELECT COUNT(user_id) WHERE cookie_id = #{cookieId}")
    Integer getUserQuantity (@Param("cookieId") Integer cookieId);

    @Select("SELECT rating from cookie_user_rating where cookie_id=#{cookieId}")
    List<Integer> getRatingByCookieId(@Param("cookieId") Integer cookieId);

//    @Select({"<script>",
//            "SELECT user_id, cookie_id, rating " +
//            "from cookie_user_rating ",
//            "<where>" +
//                "<if test='userId != null'> " +
//                "AND user_id = #{userId}",
//                "</if>",
//                "<if test='cookieId != null'> " +
//                "AND cookie_id = #{cookieId}",
//                "</if>",
//            "</where>" +
//            "</script>"})
   /* @Select( "SELECT users.id, users.login, users.name, users.role, " +
            "cookie_user_rating.rating, cookies.id, cookies.name, cookies.description, cookies.file_data, cookies.cookie_adding_status," +
            "cookies.rating " +
            "FROM cookie_user_rating " +
            "JOIN users ON users.id = cookie_user_rating.user_id " +
            "JOIN cookies ON cookie_user_rating.cookie_id = cookies.id " +
            "WHERE cookie_user_rating.user_id = #{userId}")
    @ConstructorArgs({
            @Arg(column = "users.id", javaType = Integer.class),
            @Arg(column = "login", javaType = String.class),
            @Arg(column = "users.name", javaType = String.class),
            @Arg(column = "role", javaType = Role.class),
            @Arg(column = "cookie_user_rating.rating", javaType = Integer.class),
            @Arg(column = "cookies.id", javaType = Integer.class),
            @Arg(column = "cookies.name", javaType = String.class),
            @Arg(column = "description", javaType = String.class),
            @Arg(column = "file_data", javaType = byte[].class),
            @Arg(column = "cookie_adding_status",
                            javaType = CookieAddingStatus.class),
            @Arg(column = "cookies.rating", javaType = Integer.class),

    })*/
    @Select("SELECT user_id, cookie_id, rating from cookie_user_rating " +
            "WHERE user_id = #{userId}")
    @Results({
            @Result(property = "user", column = "user_id", javaType = User.class,
                    one = @One(select = "net.cookiespoll.mapper.UserMapper.getUserById")),
            @Result(property = "cookie", column = "cookie_id", javaType = Cookie.class,
                    one = @One(select = "net.cookiespoll.mapper.CookieMapper.getById")),
            @Result(property = "rating", column = "rating", javaType = Integer.class),
    })
    List<CookieUserRating> getListByUserId(Integer userId);

    @Select("SELECT user_id, cookie_id, rating from cookie_user_rating " +
            "WHERE user_id = #{cookieId}")
    @Results({
            @Result(property = "user", column = "user_id", javaType = User.class,
                    one = @One(select = "net.cookiespoll.mapper.UserMapper.getUserById")),
            @Result(property = "cookie", column = "id", javaType = Cookie.class,
                    one = @One(select = "net.cookiespoll.mapper.CookieMapper.getById")),
            @Result(property = "rating", column = "rating", javaType = Integer.class),
    })
    List<CookieUserRating> getListByCookieId(Integer cookieId);
}
