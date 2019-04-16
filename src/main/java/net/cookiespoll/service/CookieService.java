package net.cookiespoll.service;

import net.cookiespoll.dao.CookieDao;
import net.cookiespoll.dto.AddCookieDtoRequest;
import net.cookiespoll.model.Cookie;
import net.cookiespoll.model.CookieAddingStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class CookieService {

    private CookieDao cookieDao;

    @Autowired
    public CookieService(CookieDao cookieDao) {
        this.cookieDao = cookieDao;
    }

    public Cookie addCookie (AddCookieDtoRequest addCookieDtoRequest, MultipartFile multipartFile) throws IOException {
       int rating = 0;
        return cookieDao.insert(new Cookie(addCookieDtoRequest.getName(), addCookieDtoRequest.getDescription(),
                multipartFile.getBytes(), CookieAddingStatus.WAITING, rating));
    }
}
