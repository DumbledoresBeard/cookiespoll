package net.cookiespoll.model;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public class Cookie {
    private int id;
    private String name;
    private String description;
    private byte[] fileData;

    public Cookie(int id, String name, String description, byte[] fileData) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.fileData = fileData;
    }

    public Cookie(String name, String description, byte[] fileData) {
        this.name = name;
        this.description = description;
        this.fileData = fileData;
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
}
