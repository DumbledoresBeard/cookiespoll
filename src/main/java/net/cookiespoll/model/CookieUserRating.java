package net.cookiespoll.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import net.cookiespoll.model.user.User;

import java.util.Objects;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class CookieUserRating {
    private int id;
    private User user;
    private Cookie cookie;
    private Integer rating;

    public CookieUserRating() {
    }

    public CookieUserRating(int id, User user, Cookie cookie, Integer rating) {
        this.id = id;
        this.user = user;
        this.cookie = cookie;
        this.rating = rating;
    }

    public CookieUserRating(User user, Cookie cookie, Integer rating) {
        this(0, user, cookie, rating);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    /* @JsonIgnoreProperties("usersRatings")*/
    public Cookie getCookie() {
        return cookie;
    }

    /* @JsonIgnoreProperties("usersRatings")*/
    public void setCookie(Cookie cookie) {
        this.cookie = cookie;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CookieUserRating)) return false;
        CookieUserRating that = (CookieUserRating) o;
        return getId() == that.getId() &&
                Objects.equals(getUser(), that.getUser()) &&
                Objects.equals(getCookie(), that.getCookie()) &&
                Objects.equals(getRating(), that.getRating());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUser(), getCookie(), getRating());
    }
}
