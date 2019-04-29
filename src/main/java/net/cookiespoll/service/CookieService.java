package net.cookiespoll.service;

import net.cookiespoll.dao.CookieDao;
import net.cookiespoll.dto.AddCookieRequest;
import net.cookiespoll.dto.UpdateCookieRequest;
import net.cookiespoll.model.Cookie;
import net.cookiespoll.model.CookieAddingStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class CookieService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CookieService.class);

    private CookieDao cookieDao;

    @Autowired
    public CookieService(CookieDao cookieDao) {
        this.cookieDao = cookieDao;
    }

    public Cookie addCookie (AddCookieRequest addCookieRequest, MultipartFile multipartFile, int userId)
            throws IOException {
       int rating = 0;

       LOGGER.info("Set rating={} and cookieAddingStatus={} to cookie ", rating, CookieAddingStatus.WAITING);

       return cookieDao.insert(new Cookie(addCookieRequest.getName(), addCookieRequest.getDescription(),
                                multipartFile.getBytes(), CookieAddingStatus.WAITING, rating, userId));
    }


    public List<Cookie> getCookiesByParam(String name, String description,
                                          CookieAddingStatus cookieAddingStatus, Integer rating, Integer userId) {
        return cookieDao.getByParam(name, description, cookieAddingStatus, rating, userId);
    }

    public Cookie updateCookie (UpdateCookieRequest updateCookieRequest) {
       return cookieDao.update(new Cookie(updateCookieRequest.getId(),
                updateCookieRequest.getName(), updateCookieRequest.getDescription(),
                updateCookieRequest.getFileData(), updateCookieRequest.getApprovalStatus(),
                updateCookieRequest.getRating(), updateCookieRequest.getUserId()));

    }

    public Cookie getCookieById (int id) {
        return cookieDao.getById(id);
    }
}
