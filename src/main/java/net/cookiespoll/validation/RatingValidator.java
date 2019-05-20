package net.cookiespoll.validation;

import net.cookiespoll.exception.CookieRateException;
import net.cookiespoll.model.Cookie;
import net.cookiespoll.model.CookieUserRating;

import java.util.List;

public class RatingValidator {

    public void validate(List<CookieUserRating> cookieUserRatings, Cookie cookie) throws CookieRateException {
        for (CookieUserRating cookieUserRating: cookieUserRatings) {
            if (cookieUserRating.getCookie().equals(cookie)) {
                throw new CookieRateException("This cookie already has been rated by user");
            }
        }
    }
}
