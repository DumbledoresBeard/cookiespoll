package net.cookiespoll.mapper;

import net.cookiespoll.user.User;
import net.cookiespoll.user.Role;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface UserMapper {

    @Insert("INSERT INTO users (login, name, role) VALUES "
            + "( #{user.login}, #{user.name}, #{user.role})")
    @Options(useGeneratedKeys = true, keyProperty = "user.id")
    Integer insert(@Param("user") User user);

    @Select("SELECT id, login, name, role FROM users where " +
            "id = #{id}")
    @Results({
            @Result(property = "id", column = "id", javaType = String.class),
            @Result(property = "login", column = "login", javaType = String.class),
            @Result(property = "name", column = "name", javaType = String.class),
            @Result(property = "role", column = "role", javaType = Role.class)
    })
    User getUserById(String id);
}
