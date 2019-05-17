package net.cookiespoll.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import net.cookiespoll.model.user.User;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "cookieId")
public class Cookie {
    private int cookieId;
    private String name;
    private String description;
    private byte[] fileData;
    private CookieAddingStatus cookieAddingStatus;
    private Float rating;
    private User cookieOwner;
    private List<CookieUserRating> usersRatings;

    public Cookie() {}

    public Cookie(int cookieId, String name, String description, byte[] fileData, CookieAddingStatus cookieAddingStatus,
                  Float rating, User cookieOwner, List<CookieUserRating> usersRatings) {
        this.cookieId = cookieId;
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

    public Cookie(int cookieId, String name, String description, byte[] fileData, CookieAddingStatus cookieAddingStatus, Float rating) {
        this.cookieId = cookieId;
        this.name = name;
        this.description = description;
        this.fileData = fileData;
        this.cookieAddingStatus = cookieAddingStatus;
        this.rating = rating;
    }

    public Cookie(int cookieId, String name, String description, byte[] fileData, CookieAddingStatus cookieAddingStatus,
                  Float rating, User cookieOwner) {
        this.cookieId = cookieId;
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


    public int getCookieId() {
        return cookieId;
    }

    public void setCookieId(int cookieId) {
        this.cookieId = cookieId;
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
        String ids = this.getUsersRatings()
                .stream()
                .map(CookieUserRating::getUser)
                .map(User::getId)
                .collect(Collectors.toList()).toString();
        String userIds = cookie.getUsersRatings()
                .stream()
                .map(CookieUserRating::getUser)
                .map(User::getId)
                .collect(Collectors.toList()).toString();
        String cookiesIdsThis = this.getUsersRatings()
                .stream()
                .map(CookieUserRating::getCookie)
                .map(Cookie::getCookieId)
                .collect(Collectors.toList()).toString();
        String cookiesIds = cookie.getUsersRatings()
                .stream()
                .map(CookieUserRating::getCookie)
                .map(Cookie::getCookieId)
                .collect(Collectors.toList()).toString();
        return getCookieId() == cookie.getCookieId() &&
                cookiesIdsThis.equals(cookiesIds) &&
                ids.equals(userIds);
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
                .map(Cookie::getCookieId)
                .collect(Collectors.toList());
        int result = Objects.hash(getCookieId(), ids, cookiesIdsThis);
        result = 31 * result;
        return result;
    }
}
