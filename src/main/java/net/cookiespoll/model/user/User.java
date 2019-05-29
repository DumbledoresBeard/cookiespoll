package net.cookiespoll.model.user;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import net.cookiespoll.model.Cookie;
import net.cookiespoll.model.CookieUserRating;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = User.class)
public class User {
    private String id;
    private String login;
    private String name;
    private Role role;
    private List<CookieUserRating> ratedCookies;
    private List<Cookie> addedCookies;

    public User() {}

    public User(String id, String login, String name, Role role, List<CookieUserRating> ratedCookies,
                List<Cookie> addedCookies) {
        this.id = id;
        this.login = login;
        this.name = name;
        this.role = role;
        this.ratedCookies = ratedCookies;
        this.addedCookies = addedCookies;
    }

    public User(String login, String name, Role role, List<CookieUserRating> ratedCookies, List<Cookie> addedCookies) {
        this("0" , login, name, role, ratedCookies, addedCookies);
    }

    public User(String id, String login, String name, Role role) {
        this.id = id;
        this.login = login;
        this.name = name;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<CookieUserRating> getRatedCookies() {
        return ratedCookies;
    }

    public void setRatedCookies(List<CookieUserRating> ratedCookies) {
        this.ratedCookies = ratedCookies;
    }

    public List<Cookie> getAddedCookies() {
        return addedCookies;
    }

    public void setAddedCookies(List<Cookie> addedCookies) {
        this.addedCookies = addedCookies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        String ids = "";
        String userIds = "";
        String ratedCookiesIds = "";
        String userRatedCookiesIds = "";
        String addedCookiesIds = "";
        String userAddedCookiesIds = "";

        if(this.getRatedCookies() != null) {
            ids = this.getRatedCookies()
                    .stream()
                    .map(CookieUserRating::getUser)
                    .map(User::getId)
                    .collect(Collectors.toList()).toString();
        }

        if(user.getRatedCookies() != null) {
            userIds = user.getRatedCookies()
                    .stream()
                    .map(CookieUserRating::getUser)
                    .map(User::getId)
                    .collect(Collectors.toList()).toString();
        }

        if(this.getRatedCookies() != null) {
            ratedCookiesIds = this.getRatedCookies()
                    .stream()
                    .map(CookieUserRating::getCookie)
                    .map(Cookie::getCookieId)
                    .collect(Collectors.toList()).toString();
        }

        if(user.getRatedCookies() != null) {
            userRatedCookiesIds = user.getRatedCookies()
                    .stream()
                    .map(CookieUserRating::getCookie)
                    .map(Cookie::getCookieId)
                    .collect(Collectors.toList()).toString();
        }

        if(this.getAddedCookies() != null) {
            addedCookiesIds = this.getAddedCookies()
                    .stream()
                    .map(Cookie::getCookieId)
                    .collect(Collectors.toList()).toString();
        }

        if(user.getAddedCookies() != null) {
            userAddedCookiesIds = user.getAddedCookies()
                    .stream()
                    .map(Cookie::getCookieId)
                    .collect(Collectors.toList()).toString();
        }

        return getId().equals(user.getId()) &&
                ids.equals(userIds)&&
                ratedCookiesIds.equals(userRatedCookiesIds) &&
                addedCookiesIds.equals(userAddedCookiesIds);
    }

    @Override
    public int hashCode() {
        List<String> ids = null;
        List<Integer> ratedCookiesIds = null;
        List<Integer> addedCookiesIds = null;


        if(this.getRatedCookies() != null) {
            ids = this.getRatedCookies()
                    .stream()
                    .map(CookieUserRating::getUser)
                    .map(User::getId)
                    .collect(Collectors.toList());
            ratedCookiesIds = this.getRatedCookies()
                    .stream()
                    .map(CookieUserRating::getCookie)
                    .map(Cookie::getCookieId)
                    .collect(Collectors.toList());
        }

        if(this.getAddedCookies() != null) {
            addedCookiesIds = this.getAddedCookies()
                    .stream()
                    .map(Cookie::getCookieId)
                    .collect(Collectors.toList());
        }

        return Objects.hash(getId(), ids, ratedCookiesIds, addedCookiesIds);
    }
}
