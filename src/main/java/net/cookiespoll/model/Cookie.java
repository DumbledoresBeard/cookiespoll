package net.cookiespoll.model;

import net.cookiespoll.user.User;

import java.util.Arrays;
import java.util.Objects;

public class Cookie {
    private int id;
    private String name;
    private String description;
    private byte[] fileData;
    private CookieAddingStatus cookieAddingStatus;
    private int rating;
    private User cookieOwner;

    public Cookie() {
    }

    public Cookie(int id, String name, String description, byte[] fileData,
                  CookieAddingStatus cookieAddingStatus, int rating, User cookieOwner) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.fileData = fileData;
        this.cookieAddingStatus = cookieAddingStatus;
        this.rating = rating;
        this.cookieOwner = cookieOwner;
    }

    public Cookie (String name, String description, byte[] fileData,
                   CookieAddingStatus cookieAddingStatus, int rating, User cookieOwner) {
        this (0, name, description, fileData, cookieAddingStatus, rating, cookieOwner);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }

    public CookieAddingStatus getCookieAddingStatus() {
        return cookieAddingStatus;
    }

    public void setCookieAddingStatus(CookieAddingStatus cookieAddingStatus) {
        this.cookieAddingStatus = cookieAddingStatus;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public User getCookieOwner() {
        return cookieOwner;
    }

    public void setCookieOwner(User cookieOwner) {
        this.cookieOwner = cookieOwner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cookie)) return false;
        Cookie cookie = (Cookie) o;
        return getId() == cookie.getId() &&
                getRating() == cookie.getRating() &&
                Objects.equals(getName(), cookie.getName()) &&
                Objects.equals(getDescription(), cookie.getDescription()) &&
                Arrays.equals(getFileData(), cookie.getFileData()) &&
                getCookieAddingStatus() == cookie.getCookieAddingStatus() &&
                Objects.equals(getCookieOwner(), cookie.getCookieOwner());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getId(), getName(), getDescription(), getCookieAddingStatus(), getRating(), getCookieOwner());
        result = 31 * result + Arrays.hashCode(getFileData());
        return result;
    }
}
