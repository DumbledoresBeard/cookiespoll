package net.cookiespoll.model;

import net.cookiespoll.model.user.User;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
        this(0, name, description, fileData, cookieAddingStatus, rating, cookieOwner, usersRatings);
    }

    public Cookie(int id, String name, String description, byte[] fileData, CookieAddingStatus cookieAddingStatus, Float rating) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.fileData = fileData;
        this.cookieAddingStatus = cookieAddingStatus;
        this.rating = rating;
    }

    public Cookie(int id, String name, String description, byte[] fileData, CookieAddingStatus cookieAddingStatus,
                  Float rating, User cookieOwner) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.fileData = fileData;
        this.cookieAddingStatus = cookieAddingStatus;
        this.rating = rating;
        this.cookieOwner = cookieOwner;
    }

    public Cookie(String name, String description, byte[] fileData, CookieAddingStatus cookieAddingStatus,
                   Float rating, User cookieOwner) {
        this(0, name, description, fileData, cookieAddingStatus, rating, cookieOwner);
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
        List<Integer> ids = this.getUsersRatings()
                .stream()
                .map(CookieUserRating::getUser)
                .map(User::getId)
                .collect(Collectors.toList());
        List<Integer> userIds = cookie.getUsersRatings()
                .stream()
                .map(CookieUserRating::getUser)
                .map(User::getId)
                .collect(Collectors.toList());
        List<Integer> cookiesIdsThis = this.getUsersRatings()
                .stream()
                .map(CookieUserRating::getCookie)
                .map(Cookie::getId)
                .collect(Collectors.toList());
        List<Integer> cookiesIds = cookie.getUsersRatings()
                .stream()
                .map(CookieUserRating::getCookie)
                .map(Cookie::getId)
                .collect(Collectors.toList());
        return getId() == cookie.getId() &&
                cookiesIdsThis.containsAll(cookiesIds) &&
                ids.containsAll(userIds);
                /*Objects.equals(getName(), cookie.getName()) &&
                Objects.equals(getDescription(), cookie.getDescription()) &&
                Arrays.equals(getFileData(), cookie.getFileData()) &&
                getCookieAddingStatus() == cookie.getCookieAddingStatus() &&
                Objects.equals(getRating(), cookie.getRating()) &&
                Objects.equals(getCookieOwner(), cookie.getCookieOwner()) &&
                Objects.equals(getUsersRatings(), cookie.getUsersRatings());*/
    }

    @Override
    public int hashCode() {
        List<Integer> ids = this.getUsersRatings()
                .stream()
                .map(CookieUserRating::getUser)
                .map(User::getId)
                .collect(Collectors.toList());
        List<Integer> cookiesIdsThis = this.getUsersRatings()
                .stream()
                .map(CookieUserRating::getCookie)
                .map(Cookie::getId)
                .collect(Collectors.toList());
        int result = Objects.hash(getId(), ids, cookiesIdsThis);
        result = 31 * result;
        return result;
    }
}
