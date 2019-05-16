package net.cookiespoll.mapper;

import net.cookiespoll.model.Cookie;
import net.cookiespoll.model.CookieAddingStatus;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public interface CookieMapper {

    @Insert("INSERT INTO cookies (name, description, file_data, cookie_adding_status, rating, user_id) " +
            "VALUES ( #{cookie.name}, #{cookie.description}, #{cookie.fileData}, #{cookie.cookieAddingStatus}," +
            " #{cookie.rating}, #{cookie.cookieOwner.id})")
    @Options(useGeneratedKeys = true, keyProperty = "cookie.id")
    Integer insert(@Param("cookie") Cookie cookie);

    @Select({"<script>",
            "SELECT id, name, description, file_data, cookie_adding_status, rating, user_id " +
            "FROM cookies",
            "<where>" +
                    "<if test='name != null'> " +
                    "AND name like #{name}",
                    "</if>",
                    "<if test='description != null'>" +
                    "AND description like #{description}",
                    "</if>",
                    "<if test='cookieAddingStatus != null'> " +
                    "AND cookie_adding_status like #{cookieAddingStatus}",
                    "</if>",
                    "<if test='rating != null'> " +
                    "AND rating=#{rating}",
                    "</if>",
                    "<if test='userId != null '> " +
                    "AND user_id=#{userId}",
                    "</if>",
            "</where>" +
            "</script>"})
    @Results({
            @Result(property = "cookieId", column = "id", javaType = Integer.class),
            @Result(property = "name", column = "name", javaType = String.class),
            @Result(property = "description", column = "description", javaType = String.class),
            @Result(property = "fileData", column = "file_data", javaType = byte[].class),
            @Result(property = "cookieAddingStatus", column = "cookie_adding_status",
                    javaType = CookieAddingStatus.class),
            @Result(property = "rating", column = "rating", javaType = Float.class),
            @Result(property = "cookieOwner", column = "user_id", javaType = Integer.class,
                    one = @One(select = "net.cookiespoll.mapper.UserMapper.getUserById")),
            @Result(property = "usersRatings", column = "id", javaType = List.class,
                    many = @Many(select = "net.cookiespoll.mapper.CookieUserRatingMapper.getListByCookieId")),
    })
    List<Cookie> getByParam(@Param("name") String name, @Param("description") String description,
                            @Param("cookieAddingStatus") CookieAddingStatus cookieAddingStatus,
                            @Param("rating") Float rating, @Param("userId") Integer userId);

    @Select("SELECT id, name, description, file_data FROM cookie WHERE cookie.cookie_adding_status = 'APPROVED'" +
            "AND cookie.id NOT IN (SELECT cookie_id from cookie_user_rating " +
            "WHERE cookie_user_rating.user_id = #{userId})")

    @Results({
            @Result(property = "cookieId", column = "id", javaType = Integer.class),
            @Result(property = "name", column = "name", javaType = String.class),
            @Result(property = "description", column = "description", javaType = String.class),
            @Result(property = "fileData", column = "file_data", javaType = byte[].class),
            @Result(property = "cookieAddingStatus", column = "cookie_adding_status",
                    javaType = CookieAddingStatus.class),
            @Result(property = "rating", column = "rating", javaType = Float.class),
            @Result(property = "userId", column = "user_id", javaType = Integer.class),
    })
    List<Cookie> getUnratedCookiesByUserId(@Param("userId") int userId);

    @Select("SELECT id, name, description, file_data, cookie_adding_status, rating, user_id " +
            "FROM cookies " +
            "WHERE id = #{id}")
    @Results({
            @Result(property = "cookieId", column = "id", javaType = Integer.class),
            @Result(property = "name", column = "name", javaType = String.class),
            @Result(property = "description", column = "description", javaType = String.class),
            @Result(property = "fileData", column = "file_data", javaType = byte[].class),
            @Result(property = "cookieAddingStatus", column = "cookie_adding_status",
                    javaType = CookieAddingStatus.class),
            @Result(property = "rating", column = "rating", javaType = Float.class),
            @Result(property = "cookieOwner", column = "user_id", javaType = Integer.class,
                    one = @One(select = "net.cookiespoll.mapper.UserMapper.getUserById")),
            @Result(property = "usersRatings", column = "id", javaType = List.class,
                    many = @Many(select = "net.cookiespoll.mapper.CookieUserRatingMapper.getListByCookieId")),
    })
    Cookie getById (Integer id);

    @Select("SELECT id, name, description, file_data, cookie_adding_status, rating, user_id " +
            "FROM cookies " +
            "WHERE user_id = #{id}")
    @Results({
            @Result(property = "cookieId", column = "id", javaType = Integer.class),
            @Result(property = "name", column = "name", javaType = String.class),
            @Result(property = "description", column = "description", javaType = String.class),
            @Result(property = "fileData", column = "file_data", javaType = byte[].class),
            @Result(property = "cookieAddingStatus", column = "cookie_adding_status",
                    javaType = CookieAddingStatus.class),
            @Result(property = "rating", column = "rating", javaType = Float.class),
            @Result(property = "cookieOwner", column = "user_id", javaType = Integer.class,
                    one = @One(select = "net.cookiespoll.mapper.UserMapper.getUserById")),
            @Result(property = "usersRatings", column = "id", javaType = List.class,
                    many = @Many(select = "net.cookiespoll.mapper.CookieUserRatingMapper.getListByCookieId")),
    })
    Cookie getByUserId (Integer id);

    @Update("UPDATE cookies " +
            "SET name = #{cookie.name}, description = #{cookie.description}, file_data = #{cookie.fileData}," +
            "cookie_adding_status = #{cookie.cookieAddingStatus}, rating = #{cookie.rating}, user_id = #{cookie.cookieOwner.id}" +
            " WHERE id = #{cookie.id} ")
    void update(@Param("cookie") Cookie cookie);

}

