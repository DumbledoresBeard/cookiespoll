package net.cookiespoll.dto;

public class AddCookieDtoResponse {
    private String name;
    private String description;
    private byte[] fileData;


    public AddCookieDtoResponse(String name, String description, byte[] fileData) {
        this.name = name;
        this.description = description;
        this.fileData = fileData;
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
}
