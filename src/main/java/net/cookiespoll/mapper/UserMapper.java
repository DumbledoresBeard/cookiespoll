package net.cookiespoll.mapper;

import net.cookiespoll.model.User;
import net.cookiespoll.model.UserRole;
import org.apache.ibatis.annotations.*;

public interface UserMapper {

    @Insert("INSERT INTO user (login, password, first_name, last_name, role) VALUES "
            + "( #{user.login}, #{user.password}, #{user.fistName}, #{user.lastName}," +
            " #{user.role})")
    @Options(useGeneratedKeys = true, keyProperty = "user.id")
    Integer insert(@Param("user") User user);


    @Select("SELECT id, login, password, first_name, last_name, role FROM user where" +
            "user_id = #{id}")
    @Results{{ @Result(property = "id", column = "id"),
        @Result(property = "login", column = "login", javaType = String.class),
        @Result(property = "password", column = "password", javaType = String.class),
        @Result(property = "firstName", column = "first_name", javaType = String.class),
        @Result(property = "lastName", column = "last_name", javaType = String.class),
        @Result(property = "role", column = "role", javaType = UserRole.class)
    }}
    User getUserById(int id);
}
