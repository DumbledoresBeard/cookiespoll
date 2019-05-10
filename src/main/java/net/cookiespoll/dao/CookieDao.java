package net.cookiespoll.dao;

import net.cookiespoll.dto.CookiesByParameterRequest;
import net.cookiespoll.model.Cookie;

import java.util.List;


public interface CookieDao {

    Cookie insert(Cookie cookie);

    Cookie getById(Integer id);

    List<Cookie> getByParam(CookiesByParameterRequest cookiesByParameterRequest);

    List<Cookie> getUnratedCookiesByUserId (String userId);

    Cookie update(Cookie cookie);

    void delete (Cookie cookie);
}
