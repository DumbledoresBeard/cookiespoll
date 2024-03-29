package net.cookiespoll.dto;

import net.cookiespoll.model.CookieAddingStatus;

import javax.validation.constraints.Size;

public class UpdateCookieRequest {
    private int id;

    @Size(min = 4, max = 30, message = "Cookie name must be between 4 and 30 characters")
    private String name;

    @Size(min = 1, max = 150, message = "Cookie description must be less then 150 characters and cannot be empty")
    private String description;

    private byte[] fileData;
    private CookieAddingStatus approvalStatus;
    private Float rating;

    public UpdateCookieRequest () {}

    public UpdateCookieRequest(int id, String name, String description, byte[] fileData,
                               CookieAddingStatus approvalStatus, Float rating) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.fileData = fileData;
        this.approvalStatus = approvalStatus;
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

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }
}
