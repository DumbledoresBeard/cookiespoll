package net.cookiespoll.dto;

import net.cookiespoll.model.CookieAddingStatus;
import net.cookiespoll.model.User;

public class UpdateCookieRequest {
    private int id;
    private String name;
    private String description;
    private byte[] fileData;
    private CookieAddingStatus approvalStatus;
    private int rating;
    private User cookieOwner;

    public UpdateCookieRequest() {}

    public UpdateCookieRequest(int id, String name, String description, byte[] fileData,
                               CookieAddingStatus approvalStatus, int rating, User cookieOwner) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.fileData = fileData;
        this.approvalStatus = approvalStatus;
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

    public CookieAddingStatus getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(CookieAddingStatus approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public User getCookieOwner() { return cookieOwner; }

    public void setCookieOwner(User cookieOwner) {
        this.cookieOwner = cookieOwner;
    }
}
