package net.cookiespoll.mapper;

import net.cookiespoll.user.User;
import net.cookiespoll.user.Role;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Component
public interface UserMapper {

    @Insert("INSERT INTO users (login, password, first_name, last_name, role) VALUES "
            + "( #{user.login}, #{user.name}, #{user.role})")
    @Options(useGeneratedKeys = true, keyProperty = "user.id")
    Integer insert(@Param("user") User user);

    @Select("SELECT id, login, password, first_name, last_name, role FROM users where " +
            "id = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "login", column = "login", javaType = String.class),
            @Result(property = "name", column = "first_name", javaType = String.class),
            @Result(property = "role", column = "role", javaType = Role.class)
    })

    User getUserById(int id);
}
