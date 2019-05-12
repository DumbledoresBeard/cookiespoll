package net.cookiespoll.mapper;

import net.cookiespoll.model.user.Role;
import net.cookiespoll.model.user.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserMapper {

    @Insert("INSERT INTO users (login, name, role) " +
            "VALUES ( #{user.login}, #{user.name}, #{user.role})")
    @Options(useGeneratedKeys = true, keyProperty = "user.id")
    Integer insert(@Param("user") User user);

    @Select("SELECT id, login, name, role " +
            "FROM users " +
            "WHERE id = #{id}")
    @Results({
            @Result(property = "id", column = "id", javaType = Integer.class),
            @Result(property = "login", column = "login", javaType = String.class),
            @Result(property = "name", column = "name", javaType = String.class),
            @Result(property = "role", column = "role", javaType = Role.class),
            @Result(property = "ratedCookies", column = "id", javaType = List.class,
                    many = @Many(select = "net.cookiespoll.mapper.CookieUserRatingMapper.getListByUserId")),
//            @Result(property = "addedCookies", column = "id", javaType = List.class,
//                    many = @Many(select = "net.cookiespoll.mapper.CookieMapper.getByParam")),
    })
    User getUserById(int id);

   /* @Select("SELECT id, login, name, role " +
            "FROM users " +
            "WHERE id = #{id}")
    @Results({
            @Result(property = "id", column = "id", javaType = Integer.class),
            @Result(property = "login", column = "login", javaType = String.class),
            @Result(property = "name", column = "name", javaType = String.class),
            @Result(property = "role", column = "role", javaType = Role.class),
    })

    User getUser(int id);*/
}
