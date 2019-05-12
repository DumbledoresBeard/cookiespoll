package net.cookiespoll.model;

import net.cookiespoll.model.user.User;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Cookie {
    private int id;
    private String name;
    private String description;
    private byte[] fileData;
    private CookieAddingStatus cookieAddingStatus;
    private Float rating;
    private User cookieOwner;
    private List<CookieUserRating> usersRatings;

    public Cookie() {}

    public Cookie(int id, String name, String description, byte[] fileData, CookieAddingStatus cookieAddingStatus,
                  Float rating, User cookieOwner, List<CookieUserRating> usersRatings) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.fileData = fileData;
        this.cookieAddingStatus = cookieAddingStatus;
        this.rating = rating;
        this.cookieOwner = cookieOwner;
        this.usersRatings = usersRatings;
    }

    public Cookie(String name, String description, byte[] fileData, CookieAddingStatus cookieAddingStatus, Float rating,
                  User cookieOwner, List<CookieUserRating> usersRatings) {
        this.name = name;
        this.description = description;
        this.fileData = fileData;
        this.cookieAddingStatus = cookieAddingStatus;
        this.rating = rating;
        this.cookieOwner = cookieOwner;
        this.usersRatings = usersRatings;
    }

    public Cookie(int id, String name, String description, byte[] fileData, CookieAddingStatus cookieAddingStatus, Float rating,
                  User cookieOwner) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.fileData = fileData;
        this.cookieAddingStatus = cookieAddingStatus;
        this.rating = rating;
        this.cookieOwner = cookieOwner;
    }

    public Cookie(int id, String name, String description, byte[] fileData, CookieAddingStatus cookieAddingStatus, Float rating) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.fileData = fileData;
        this.cookieAddingStatus = cookieAddingStatus;
        this.rating = rating;
    }

    public Cookie(String name, String description, byte[] fileData, CookieAddingStatus cookieAddingStatus,
                  Float rating, User cookieOwner) {
        this.name = name;
        this.description = description;
        this.fileData = fileData;
        this.cookieAddingStatus = cookieAddingStatus;
        this.rating = rating;
        this.cookieOwner = cookieOwner;
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

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public User getCookieOwner() {
        return cookieOwner;
    }

    public void setCookieOwner(User cookieOwner) {
        this.cookieOwner = cookieOwner;
    }

    public List<CookieUserRating> getUsersRatings() {
        return usersRatings;
    }

    public void setUsersRatings(List<CookieUserRating> usersRatings) {
        this.usersRatings = usersRatings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cookie)) return false;
        Cookie cookie = (Cookie) o;
        return getId() == cookie.getId() &&
                Objects.equals(getName(), cookie.getName()) &&
                Objects.equals(getDescription(), cookie.getDescription()) &&
                Arrays.equals(getFileData(), cookie.getFileData()) &&
                getCookieAddingStatus() == cookie.getCookieAddingStatus() &&
                Objects.equals(getRating(), cookie.getRating()) &&
                Objects.equals(getCookieOwner(), cookie.getCookieOwner()) &&
                Objects.equals(getUsersRatings(), cookie.getUsersRatings());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getId(), getName(), getDescription(), getCookieAddingStatus(), getRating(), getCookieOwner(), getUsersRatings());
        result = 31 * result + Arrays.hashCode(getFileData());
        return result;
    }
}
