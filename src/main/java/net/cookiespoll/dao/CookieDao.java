package net.cookiespoll.dao;

import net.cookiespoll.dto.CookiesByParameterRequest;
import net.cookiespoll.model.Cookie;
import net.cookiespoll.model.CookieAddingStatus;

import java.util.List;


public interface CookieDao {

    Cookie insert(Cookie cookie);

    Cookie getById(Integer id);

    List<Cookie> getByParam(String name, String description, CookieAddingStatus cookieAddingStatus, Float rating,
                            String userId);

    List<Cookie> getUnratedByUserId (String userId);

    Cookie update(Cookie cookie);

    Integer delete (Integer id);
}
