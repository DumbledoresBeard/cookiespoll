package net.cookiespoll.dto;

import org.springframework.web.multipart.MultipartFile;

public class AddCookieDtoResponse {
    private String name;
    private String description;
    private MultipartFile file;

    public AddCookieDtoResponse(String name, String description, MultipartFile file) {
        this.name = name;
        this.description = description;
        this.file = file;
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

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
