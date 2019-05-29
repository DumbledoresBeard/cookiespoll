package net.cookiespoll.dto.mapper;

import net.cookiespoll.dto.*;
import net.cookiespoll.model.Cookie;
import net.cookiespoll.model.user.User;
import org.springframework.stereotype.Component;

@Component
public class CookieDtoConverter {

    public CookieDtoConverter() {
    }

    public Cookie convertDto(UpdateCookieRequest updateCookieRequest) {
        return new Cookie(updateCookieRequest.getId(), updateCookieRequest.getName(), updateCookieRequest.getDescription(),
                updateCookieRequest.getFileData(), updateCookieRequest.getApprovalStatus(), updateCookieRequest.getRating(),
                updateCookieRequest.getCookieOwner());
    }

    public UpdateCookieResponse convertToUpdateResponse(Cookie cookie) {
        return new UpdateCookieResponse(cookie.getCookieId(), cookie.getName(), cookie.getDescription(), cookie.getFileData(),
                cookie.getCookieAddingStatus(), cookie.getRating(), cookie.getCookieOwner());
    }

    public AddCookieResponse convertToAddCookieResponse(Cookie cookie) {
        return new AddCookieResponse(cookie.getCookieId(), cookie.getName(), cookie.getDescription(),
                cookie.getFileData(), cookie.getCookieAddingStatus());
    }

    public RateCookieResponse convertToRateCookieResponse (Cookie cookie, Integer rating) {
        CookieOwner cookieOwner = new CookieOwner(cookie.getCookieOwner().getId(),
                cookie.getCookieOwner().getLogin(),cookie.getCookieOwner().getName(), cookie.getCookieOwner().getRole());
        return new RateCookieResponse(cookie.getCookieId(), cookie.getName(), cookie.getDescription(),
                cookie.getFileData(), cookie.getCookieAddingStatus(), cookie.getRating(), cookieOwner,
                rating);
    }


}
