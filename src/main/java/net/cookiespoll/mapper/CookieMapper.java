package net.cookiespoll.mapper;

import net.cookiespoll.model.Cookie;
import net.cookiespoll.model.CookieAddingStatus;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;


@Component
public interface CookieMapper {

    @Insert("INSERT INTO cookie (name, description, file_data, cookie_adding_status, rating) VALUES "
            + "( #{cookie.name}, #{cookie.description}, #{cookie.fileData}, #{cookie.cookieAddingStatus}," +
            " #{cookie.rating})")
    @Options(useGeneratedKeys = true, keyProperty = "cookie.id")
    Integer insert(@Param("cookie") Cookie cookie);

    @Select("SELECT id, name, description, file_data, cookie_adding_status, rating FROM cookie" +
            " WHERE cookie_adding_status = #{cookieAddingStatus}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name", javaType = String.class),
            @Result(property = "description", column = "description", javaType = String.class),
            @Result(property = "fileData", column = "file_data", javaType = byte[].class),
            @Result(property = "cookieAddingStatus", column = "cookie_adding_status", javaType = CookieAddingStatus.class),
            @Result(property = "rating", column = "rating", javaType = Integer.class),
    })
    List<Cookie> getByCookieAddingStatus(CookieAddingStatus cookieAddingStatus);

    @Update("UPDATE cookie SET name = #{name}, description = #{description}, file_data = #{fileData}," +
            "cookie_adding_status = #{cookieAddingStatus}, rating = #{rating}" +
            " WHERE id = #{id} ")
    void update(Cookie cookie);





}

