package net.cookiespoll.mapper;


import net.cookiespoll.model.user.Admin;
import net.cookiespoll.model.user.Role;
import net.cookiespoll.model.user.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserMapper {

    @Insert("INSERT INTO users (id, login, name, role) " +
            "VALUES ( #{user.id}, #{user.login}, #{user.name}, #{user.role})")
    void insert(@Param("user") User user);

    @Select("SELECT id, login, name, role " +
            "FROM users " +
            "WHERE id = #{id}")
    @Results({
            @Result(property = "id", column = "id", javaType = String.class),
            @Result(property = "login", column = "login", javaType = String.class),
            @Result(property = "name", column = "name", javaType = String.class),
            @Result(property = "role", column = "role", javaType = Role.class),
            @Result(property = "ratedCookies", column = "id", javaType = List.class,
                    many = @Many(select = "net.cookiespoll.mapper.CookieUserRatingMapper.getListByUserId", fetchType = FetchType.LAZY)),
            @Result(property = "addedCookies", column = "id", javaType = List.class,
                    many = @Many(select = "net.cookiespoll.mapper.CookieMapper.getByUserId", fetchType = FetchType.LAZY)),
    })
    User getById(String id);


    @Update({"<script>",
            "UPDATE users SET login = #{user.login}, name = #{user.name}, role = #{user.role}" +
            " WHERE id = #{user.id}; ",

            "INSERT INTO cookie_user_rating (user_id, cookie_id, rating) " +
            "VALUES " +

            "<foreach item='cookieUserRating' collection='user.ratedCookies' separator=','>",
                "(#{cookieUserRating.user.id}, #{cookieUserRating.cookie.cookieId}, #{cookieUserRating.rating}) ",
                "ON CONFLICT (user_id, cookie_id) " +
                "DO UPDATE SET (user_id, cookie_id, rating) = (#{cookieUserRating.user.id}, " +
                "#{cookieUserRating.cookie.cookieId}, #{cookieUserRating.rating})",
            "</foreach>",

            "</script>"})
    void update(@Param("user") User user);

    @Select("SELECT id, login " +
            "FROM admins")
    @Results({
            @Result(property = "id", column = "id", javaType = Integer.class),
            @Result(property = "login", column = "login", javaType = String.class),
    })
    List<Admin> getAdmins();

}
