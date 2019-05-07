package net.cookiespoll.service;

import net.cookiespoll.dao.CookieDao;
import net.cookiespoll.dto.AddCookieRequest;
import net.cookiespoll.dto.CookiesByParameterRequest;
import net.cookiespoll.dto.UpdateCookieRequest;
import net.cookiespoll.model.Cookie;
import net.cookiespoll.model.CookieAddingStatus;
import net.cookiespoll.model.User;
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

    public Cookie insert(AddCookieRequest addCookieRequest, MultipartFile multipartFile, User cookieOwner)
                        throws IOException {
       int rating = 0;

       LOGGER.info("Set rating={} and cookieAddingStatus={} to cookie ", rating, CookieAddingStatus.WAITING);

       return cookieDao.insert(new Cookie(addCookieRequest.getName(), addCookieRequest.getDescription(),
                                multipartFile.getBytes(), CookieAddingStatus.WAITING, rating, cookieOwner));
    }


    public List<Cookie> getByParam(CookiesByParameterRequest cookiesByParameterRequest) {
        return cookieDao.getByParam(cookiesByParameterRequest);
    }


    public Cookie update(UpdateCookieRequest updateCookieRequest) {
       return cookieDao.update(new Cookie(updateCookieRequest.getId(),
                updateCookieRequest.getName(), updateCookieRequest.getDescription(),
                updateCookieRequest.getFileData(), updateCookieRequest.getApprovalStatus(),
                updateCookieRequest.getRating(), updateCookieRequest.getCookieOwner()));
    }

    public Cookie getById(Integer id) {
        return cookieDao.getById(id);
    }
}
