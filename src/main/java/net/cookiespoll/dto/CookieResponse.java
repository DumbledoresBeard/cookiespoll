package net.cookiespoll.dto;

import net.cookiespoll.model.CookieAddingStatus;

public class CookieResponse {
    private int id;
    private String name;
    private String description;
    private byte[] fileData;
    private CookieAddingStatus cookieAddingStatus;
    private Float rating;
    private CookieOwner cookieOwner;

    public CookieResponse() {
    }

    public CookieResponse(int id, String name, String description, byte[] fileData,
                          CookieAddingStatus cookieAddingStatus, Float rating, CookieOwner cookieOwner) {
        this.id = id;
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

    public CookieOwner getCookieOwner() {
        return cookieOwner;
    }

    public void setCookieOwner(CookieOwner cookieOwner) {
        this.cookieOwner = cookieOwner;
    }
}
