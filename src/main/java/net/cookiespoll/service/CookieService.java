package net.cookiespoll.service;

import net.cookiespoll.dao.CookieDao;
import net.cookiespoll.dto.AddCookieRequest;
import net.cookiespoll.dto.CookiesByParameterRequest;
import net.cookiespoll.model.Cookie;
import net.cookiespoll.model.CookieAddingStatus;
import net.cookiespoll.model.CookieUserRating;
import net.cookiespoll.model.user.User;
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
       float rating = 0;

       LOGGER.info("Set rating={} and cookieAddingStatus={} to cookie ", rating, CookieAddingStatus.WAITING);

       return cookieDao.insert(new Cookie(addCookieRequest.getName(), addCookieRequest.getDescription(),
                                multipartFile.getBytes(), CookieAddingStatus.WAITING, rating, cookieOwner));
    }

    public List<Cookie> getByParam(CookiesByParameterRequest cookiesByParameterRequest) {
        return cookieDao.getByParam(cookiesByParameterRequest);
    }

    public Cookie update(Cookie cookie) {
        return cookieDao.update(cookie);
    }

    public Cookie getById(Integer id) {
        return cookieDao.getById(id);
    }

    public List<Cookie> getUnratedByUserId(int userId) {
        return cookieDao.getUnratedCookiesByUserId(userId);
    }

    public Float countRating(Cookie cookie) {
        List<CookieUserRating> cookieUserRatingList = this.getById(cookie.getId()).getUsersRatings();
        Long usersQuantity = cookieUserRatingList.stream().count();
        Integer cookieRatingSum = cookieUserRatingList.stream()
                .mapToInt(cookieUserRating -> cookieUserRating.getRating()).sum();
        return  (float)(cookieRatingSum / usersQuantity);
    }

}
