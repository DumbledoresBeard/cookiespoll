package net.cookiespoll.dto;
import net.cookiespoll.model.Cookie;

public class RateCookieResponse {

    private Cookie cookie;
    private Integer rating;

    public RateCookieResponse() {
    }

    public RateCookieResponse(Cookie cookie, Integer rating) {
        this.cookie = cookie;
        this.rating = rating;
    }

    public Cookie getCookie() {
        return cookie;
    }

    public void setCookie(Cookie cookie) {
        this.cookie = cookie;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
