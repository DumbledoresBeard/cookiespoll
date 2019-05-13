package net.cookiespoll.model.user;

import net.cookiespoll.model.Cookie;
import net.cookiespoll.model.CookieUserRating;

import java.util.List;
import java.util.Objects;

public class User {
    private int id;
    private String login;
    private String name;
    private Role role;
    private List<CookieUserRating> ratedCookies;
    private List<Cookie> addedCookies;

    public User() {}

    public User(int id, String login, String name, Role role, List<CookieUserRating> ratedCookies, List<Cookie> addedCookies) {
        this.id = id;
        this.login = login;
        this.name = name;
        this.role = role;
        this.ratedCookies = ratedCookies;
        this.addedCookies = addedCookies;
    }

    public User(String login, String name, Role role, List<CookieUserRating> ratedCookies, List<Cookie> addedCookies) {
        this(0 , login, name, role, ratedCookies, addedCookies);
    }

    public User(int id, String login, String name, Role role) {
        this.id = id;
        this.login = login;
        this.name = name;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
        return getId() == user.getId() &&
                Objects.equals(getLogin(), user.getLogin()) &&
                Objects.equals(getName(), user.getName()) &&
                getRole() == user.getRole() &&
                Objects.equals(getRatedCookies(), user.getRatedCookies()) &&
                Objects.equals(getAddedCookies(), user.getAddedCookies());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getLogin(), getName(), getRole(), getRatedCookies(), getAddedCookies());
    }
}
