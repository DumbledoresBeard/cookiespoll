package net.cookiespoll.dao;

import net.cookiespoll.model.Cookie;
import net.cookiespoll.model.CookieAddingStatus;

import java.util.List;
import java.util.Optional;


public interface CookieDao {

    Cookie insert(Cookie cookie);

    Cookie getById(Integer id);

    Optional<Cookie> getByName(String name);

    List<Cookie> getByParam(String name, String description, CookieAddingStatus cookieAddingStatus, Float rating,
                            String userId);

    List<Cookie> getUnratedByUserId (String userId);

    Cookie update(Cookie cookie);

    Integer delete (Integer id);
}
