package net.cookiespoll.dto.mapper;

import net.cookiespoll.dto.*;
import net.cookiespoll.model.Cookie;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CookieDtoConverter {

    public CookieDtoConverter() {
    }

    public Cookie convertToCookie(UpdateCookieRequest updateCookieRequest) {
        return new Cookie(updateCookieRequest.getId(), updateCookieRequest.getName(), updateCookieRequest.getDescription(), updateCookieRequest.getFileData(),
                updateCookieRequest.getApprovalStatus(), updateCookieRequest.getRating());
    }

    public UpdateCookieResponse convertToUpdateResponse(Cookie cookie) {
        return new UpdateCookieResponse(cookie.getCookieId(), cookie.getName(), cookie.getDescription(), cookie.getFileData(),
                cookie.getCookieAddingStatus(), cookie.getRating(), cookie.getCookieOwner());
    }

    public RateCookieResponse convertToRateCookieResponse(Cookie cookie, Integer rating) {
        CookieOwner cookieOwner = new CookieOwner(cookie.getCookieOwner().getId(),
                cookie.getCookieOwner().getLogin(),cookie.getCookieOwner().getName(), cookie.getCookieOwner().getRole());
        return new RateCookieResponse(cookie.getCookieId(), cookie.getName(), cookie.getDescription(),
                cookie.getFileData(), cookie.getCookieAddingStatus(), cookie.getRating(), cookieOwner,
                rating);
    }

    public CookieResponse convertToCookieResponse(Cookie cookie) {
        CookieOwner cookieOwner = new CookieOwner(cookie.getCookieOwner().getId(),
                cookie.getCookieOwner().getLogin(),cookie.getCookieOwner().getName(), cookie.getCookieOwner().getRole());
        return new CookieResponse(cookie.getCookieId(), cookie.getName(), cookie.getDescription(),
                cookie.getFileData(), cookie.getCookieAddingStatus(), cookie.getRating(), cookieOwner);
    }

    public List<CookieResponse> convertToListOfCookieResponses(List<Cookie> cookies) {
        List<CookieResponse> cookieResponses = new ArrayList<>();
        for (Cookie cookie: cookies) {
            cookieResponses.add(this.convertToCookieResponse(cookie));
        }
        return cookieResponses;
    }
}
