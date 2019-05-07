package net.cookiespoll.user;


import java.util.Objects;

public class User {
    private int id;
    private String login;
    private String name;
    private Role role;

    public User() { }

    public User(int id, String login, String name, Role role) {
        this.id = id;
        this.login = login;
        this.name = name;
        this.role = role;
    }

    public User(String login, String name, Role role) {
        this (0, login, name, role);
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
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getId() == user.getId() &&
                Objects.equals(getLogin(), user.getLogin()) &&
                Objects.equals(getName(), user.getName()) &&
                getRole() == user.getRole();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getLogin(), getName(), getRole());
    }
}
