package net.cookiespoll.validation;

import net.cookiespoll.exception.NotUniqueCookieNameException;
import net.cookiespoll.model.Cookie;
import net.cookiespoll.service.CookieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CookieNameUniquenessValidator {

    private CookieService cookieService;

    @Autowired
    public CookieNameUniquenessValidator(CookieService cookieService) {
        this.cookieService = cookieService;
    }

    public void validate(String name) throws NotUniqueCookieNameException {
        if(!cookieService.getByParam(name, null, null, null, null).isEmpty()) {
            throw new NotUniqueCookieNameException("Cookie name is not unique");
        }
    }
}
