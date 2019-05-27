package net.cookiespoll.dto;

import net.cookiespoll.model.CookieAddingStatus;
import net.cookiespoll.model.user.User;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

public class RateCookieRequest {
    private Integer id;

    @Size(min = 4, max = 30, message = "Cookie name must be between 4 and 30 characters")
    private String name;

    @Size(min = 1, max = 150, message = "Cookie description must be less then 150 characters and cannot be empty")
    private String description;

    private byte[] fileData;
    private CookieAddingStatus approvalStatus;
    private Float resultRating;
    private CookieOwner cookieOwner;

    @Min(value = 1, message = "Rating must be a digit between 1 and 5")
    @Max(value = 5, message = "Rating must be a digit between 1 and 5")
    private Integer rating;

    public RateCookieRequest() {
    }

    public RateCookieRequest(Integer id, String name, String description, byte[] fileData,
                             CookieAddingStatus approvalStatus, Float resultRating, CookieOwner cookieOwner, Integer rating) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.fileData = fileData;
        this.approvalStatus = approvalStatus;
        this.resultRating = resultRating;
        this.cookieOwner = cookieOwner;
        this.rating = rating;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CookieOwner getCookieOwner() { return cookieOwner; }

    public void setCookieOwner(CookieOwner cookieOwner) {
        this.cookieOwner = cookieOwner;
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

    public Float getResultRating() {
        return resultRating;
    }

    public void setResultRating(Float resultRating) {
        this.resultRating = resultRating;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }


}
