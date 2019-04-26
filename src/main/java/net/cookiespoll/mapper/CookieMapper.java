package net.cookiespoll.mapper;

import io.swagger.models.auth.In;
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

    @Select({"<script>",
            "SELECT id, name, description, file_data, cookie_adding_status, rating FROM cookie",
            "<where>" +
//                    "<if test='id != null and name != null and description != null and  " +
//                    "cookieAddingStatus != null and rating != null and userId != null'>id=#{id} AND name=#{name} " +
//                    "AND description=#{description} AND cookie_adding_status=#{cookieAddingStatus} " +
//                    "AND rating=#{rating} AND user_id=#{userId}",
//                    "</if>",
//                    "<if test='id != null and name != null and description != null and  " +
//                    "cookieAddingStatus != null and rating != null '>id=#{id} AND name=#{name} " +
//                    "AND description=#{description} AND cookie_adding_status=#{cookieAddingStatus} " +
//                    "AND rating=#{rating}",
//                    "</if>",
//                    "<if test='id != null and name != null and description != null and  " +
//                    "cookieAddingStatus != null'>id=#{id} AND name=#{name} " +
//                    "AND description=#{description} AND cookie_adding_status=#{cookieAddingStatus}",
//                    "</if>",
//                    "<if test='id != null and name != null and description != null'>id=#{id} " +
//                    "AND name=#{name} AND description=#{description}",
//                    "</if>",
//                    "<if test='id != null and name != null'>id=#{id} AND name=#{name}",
//                    "</if>",
                    "<if test='id != null'>id=#{id}",
                    "</if>",
                    "<if test='name != null'> AND name like #{name}",
                    "</if>",
                    "<if test='description != null'> AND description like #{description}",
                    "</if>",
                    "<if test='cookieAddingStatus != null'> AND cookie_adding_status like #{cookieAddingStatus}",
                    "</if>",
                    "<if test='rating != null'> AND rating like #{rating}",
                    "</if>",
                    "<if test='userId != null '> AND user_id like #{userId}",
                    "</if>",
                    "</where>" +
                    "</script>"})
    @Results({
            @Result(property = "id", column = "id", javaType = Integer.class),
            @Result(property = "name", column = "name", javaType = String.class),
            @Result(property = "description", column = "description", javaType = String.class),
            @Result(property = "fileData", column = "file_data", javaType = byte[].class),
            @Result(property = "cookieAddingStatus", column = "cookie_adding_status", javaType = CookieAddingStatus.class),
            @Result(property = "rating", column = "rating", javaType = Integer.class),
    })
    List<Cookie> getByParam(@Param("id") Integer id, @Param("name") String name, @Param("description")
                            String description, @Param("cookieAddingStatus") CookieAddingStatus cookieAddingStatus,
                            @Param("rating") int rating, @Param("userId") Integer userId);


    @Update("UPDATE cookie SET name = #{name}, description = #{description}, file_data = #{fileData}," +
            "cookie_adding_status = #{cookieAddingStatus}, rating = #{rating}" +
            " WHERE id = #{id} ")
    void update(Cookie cookie);





}

