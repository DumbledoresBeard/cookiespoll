package net.cookiespoll.dto;

import net.cookiespoll.model.CookieAddingStatus;

public class AddCookieDtoResponse {
    private int id;
    private String name;
    private String description;
    private byte[] fileData;
    private CookieAddingStatus cookieAddingStatus;

    public AddCookieDtoResponse () {}

    public AddCookieDtoResponse(int id, String name, String description, byte[] fileData, CookieAddingStatus cookieAddingStatus) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.fileData = fileData;
        this.cookieAddingStatus = cookieAddingStatus;
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
}
