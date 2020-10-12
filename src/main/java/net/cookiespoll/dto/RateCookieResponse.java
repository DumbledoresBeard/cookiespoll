package net.cookiespoll.dto;
import net.cookiespoll.model.CookieAddingStatus;

public class RateCookieResponse {

    private Integer id;
    private String name;
    private String description;
    private byte[] fileData;
    private CookieAddingStatus approvalStatus;
    private Float overallRating;
    private CookieOwner cookieOwner;
    private Integer ratingGivenByUser;

    public RateCookieResponse() {
    }

    public RateCookieResponse(Integer id, String name, String description, byte[] fileData,
                              CookieAddingStatus approvalStatus, Float overallRating,
                              CookieOwner cookieOwner, Integer ratingGivenByUser) {
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

    public CookieOwner getCookieOwner() {
        return cookieOwner;
    }

    public void setCookieOwner(CookieOwner cookieOwner) {
        this.cookieOwner = cookieOwner;
    }

    public Integer getRatingGivenByUser() {
        return ratingGivenByUser;
    }

    public void setRatingGivenByUser(Integer ratingGivenByUser) {
        this.ratingGivenByUser = ratingGivenByUser;
    }
}
