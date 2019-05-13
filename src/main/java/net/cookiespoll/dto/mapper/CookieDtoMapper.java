package net.cookiespoll.dto.mapper;

import io.swagger.models.auth.In;
import net.cookiespoll.dto.*;
import net.cookiespoll.model.Cookie;
import org.springframework.stereotype.Component;

@Component
public class CookieDtoMapper {

    public CookieDtoMapper() {
    }

    public Cookie convertDto(UpdateCookieRequest updateCookieRequest) {
        return new Cookie(updateCookieRequest.getId(), updateCookieRequest.getName(), updateCookieRequest.getDescription(),
                updateCookieRequest.getFileData(), updateCookieRequest.getApprovalStatus(), updateCookieRequest.getRating(),
                updateCookieRequest.getCookieOwner());
    }

    public Cookie convertDto(RateCookieRequest rateCookieRequest) {
        return new Cookie(rateCookieRequest.getId(), rateCookieRequest.getName(), rateCookieRequest.getDescription(),
                rateCookieRequest.getFileData(), rateCookieRequest.getApprovalStatus(), rateCookieRequest.getResultRating(),
                rateCookieRequest.getCookieOwner());
    }

    public UpdateCookieResponse convertToUpdateResponse(Cookie cookie) {
        return new UpdateCookieResponse(cookie.getId(), cookie.getName(), cookie.getDescription(), cookie.getFileData(),
                cookie.getCookieAddingStatus(), cookie.getRating(), cookie.getCookieOwner());
    }

    public AddCookieResponse convertToAddCookieResponse(Cookie cookie) {
        return new AddCookieResponse(cookie.getId(), cookie.getName(), cookie.getDescription(),
                cookie.getFileData(), cookie.getCookieAddingStatus());
    }

    public RateCookieResponse convertToRateCookieResponse (Cookie cookie, Integer rating) {
        return new RateCookieResponse(cookie.getId(), cookie.getName(), cookie.getDescription(),
                cookie.getFileData(), cookie.getCookieAddingStatus(), cookie.getRating(), cookie.getCookieOwner(),
                rating);
    }


}
