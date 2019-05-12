package net.cookiespoll.dto.mapper;

import net.cookiespoll.dto.AddCookieResponse;
import net.cookiespoll.dto.RateCookieRequest;
import net.cookiespoll.dto.UpdateCookieRequest;
import net.cookiespoll.dto.UpdateCookieResponse;
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


}
