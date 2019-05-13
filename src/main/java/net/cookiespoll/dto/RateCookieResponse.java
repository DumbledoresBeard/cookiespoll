package net.cookiespoll.dto;
import net.cookiespoll.model.Cookie;
import net.cookiespoll.model.CookieAddingStatus;
import net.cookiespoll.model.user.Role;
import net.cookiespoll.model.user.User;

import javax.validation.constraints.Size;

public class RateCookieResponse {

    private Integer id;
    private String name;
    private String description;
    private byte[] fileData;
    private CookieAddingStatus approvalStatus;
    private Float overallRating;
    private User cookieOwner;
    private Integer ratingGivenByUser;

    public RateCookieResponse() {
    }

    public RateCookieResponse(Integer id, String name, String description, byte[] fileData,
                              CookieAddingStatus approvalStatus, Float overallRating, User cookieOwner, Integer ratingGivenByUser) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.fileData = fileData;
        this.approvalStatus = approvalStatus;
        this.overallRating = overallRating;
        this.cookieOwner = cookieOwner;
        this.ratingGivenByUser = ratingGivenByUser;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public CookieAddingStatus getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(CookieAddingStatus approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public Float getOverallRating() {
        return overallRating;
    }

    public void setOverallRating(Float overallRating) {
        this.overallRating = overallRating;
    }

    public User getCookieOwner() {
        return cookieOwner;
    }

    public void setCookieOwner(User cookieOwner) {
        this.cookieOwner = cookieOwner;
    }

    public Integer getRatingGivenByUser() {
        return ratingGivenByUser;
    }

    public void setRatingGivenByUser(Integer ratingGivenByUser) {
        this.ratingGivenByUser = ratingGivenByUser;
    }
}
