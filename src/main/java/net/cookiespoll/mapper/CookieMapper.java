package net.cookiespoll.mapper;

import net.cookiespoll.model.Cookie;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;


@Component
public interface CookieMapper {

    @Insert("INSERT INTO cookie (name, description, file_data, cookie_adding_status, rating) VALUES "
            + "( #{cookie.name}, #{cookie.description}, #{cookie.fileData}, #{cookie.cookieAddingStatus}," +
            " #{cookie.rating})")
    @Options(useGeneratedKeys = true, keyProperty = "expense.id")
    Integer insert(@Param("cookie") Cookie cookie);
}
