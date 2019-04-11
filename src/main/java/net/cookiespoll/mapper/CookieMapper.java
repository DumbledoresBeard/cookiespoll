package net.cookiespoll.mapper;

import net.cookiespoll.model.Cookie;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;



public interface CookieMapper {

    @Insert("INSERT INTO cookie (name, description, file_data, is_approved) VALUES "
            + "( #{cookie.name}, #{cookie.description}, #{cookie.fileData}, #{cookie.isApproved} )")
    @Options(useGeneratedKeys = true, keyProperty = "expense.id")
    Integer insert(@Param("cookie") Cookie cookie);
}
