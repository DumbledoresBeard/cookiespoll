package net.cookiespoll.dto;

import net.cookiespoll.model.user.Role;

import java.util.Objects;

public class CookieOwner {

    private int id;
    private String login;
    private String name;
    private Role role;

    public CookieOwner() {
    }

    public CookieOwner(int id, String login, String name, Role role) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CookieOwner)) return false;
        CookieOwner that = (CookieOwner) o;
        return id == that.id &&
                Objects.equals(login, that.login) &&
                Objects.equals(name, that.name) &&
                role == that.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, name, role);
    }
}
