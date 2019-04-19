package net.cookiespoll.service;

import net.cookiespoll.dao.CookieDao;
import net.cookiespoll.dao.UserDao;
import net.cookiespoll.dto.AddCookieDtoRequest;
import net.cookiespoll.model.Cookie;
import net.cookiespoll.model.CookieAddingStatus;
import net.cookiespoll.model.User;
import net.cookiespoll.model.UserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class CookieService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CookieService.class);

    private CookieDao cookieDao;
    private UserDao userDao;

    @Autowired
    public CookieService(CookieDao cookieDao, UserDao userDao) {
        this.cookieDao = cookieDao;
        this.userDao = userDao;
    }

    public Cookie addCookie (AddCookieDtoRequest addCookieDtoRequest, MultipartFile multipartFile) throws IOException {
       int rating = 0;

       LOGGER.info("Set rating={} and cookieAddingStatus={} to cookie ", rating, CookieAddingStatus.WAITING);

       return cookieDao.insert(new Cookie(addCookieDtoRequest.getName(), addCookieDtoRequest.getDescription(),
                                multipartFile.getBytes(), CookieAddingStatus.WAITING, rating));
    }

    public UserRole getUserRole (int id) {
       User user = userDao.getUserById(id);
       return user.getRole();
    }
}
