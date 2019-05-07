package net.cookiespoll.dao;

import net.cookiespoll.dto.CookiesByParameterRequest;
import net.cookiespoll.model.Cookie;
import net.cookiespoll.model.CookieAddingStatus;
import net.cookiespoll.user.User;

import java.util.List;


public interface CookieDao {

    Cookie insert(Cookie cookie);

    Cookie getById(Integer id);

    List<Cookie> getByParam(CookiesByParameterRequest cookiesByParameterRequest);

    Cookie update(Cookie cookie);

    void delete (Cookie cookie);
}
