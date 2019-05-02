package net.cookiespoll.user;


public class User {
    private String id;
    private String login;
    private String name;
    private Role role;

    public User() { }

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
}
