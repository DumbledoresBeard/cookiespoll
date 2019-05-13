package net.cookiespoll.model;

import net.cookiespoll.model.user.User;

import java.util.Objects;


public class CookieUserRating {

    private User user;
    private Cookie cookie;
    private Integer rating;

    public CookieUserRating() {}

    public User getUser() { return user; }

    public void setUser(User user) {
        this.user = user;
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

    public void setRating(Integer rating) { this.rating = rating; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CookieUserRating)) return false;
        CookieUserRating that = (CookieUserRating) o;
        return Objects.equals(getUser(), that.getUser()) &&
                Objects.equals(getCookie(), that.getCookie()) &&
                Objects.equals(getRating(), that.getRating());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUser(), getCookie(), getRating());
    }
}
