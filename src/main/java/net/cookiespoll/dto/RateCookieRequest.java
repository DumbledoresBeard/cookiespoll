package net.cookiespoll.dto;

import net.cookiespoll.model.CookieAddingStatus;

public class RateCookieRequest {
    private int id;
    private String name;
    private String description;
    private byte[] fileData;
    private CookieAddingStatus approvalStatus;
    private Float resultRating;
    private String userId;
    private Integer rating;

    public RateCookieRequest() {
    }

    public RateCookieRequest(int id, String name, String description, byte[] fileData,
                             CookieAddingStatus approvalStatus, Float resultRating, String userId,
                             Integer rating) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.fileData = fileData;
        this.approvalStatus = approvalStatus;
        this.resultRating = resultRating;
        this.userId = userId;
        this.rating = rating;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
