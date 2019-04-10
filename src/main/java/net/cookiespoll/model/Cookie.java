package net.cookiespoll.model;

import java.util.Arrays;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cookie)) return false;
        Cookie cookie = (Cookie) o;
        return getId() == cookie.getId() &&
                Objects.equals(getName(), cookie.getName()) &&
                Objects.equals(getDescription(), cookie.getDescription()) &&
                Arrays.equals(getFileData(), cookie.getFileData());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getId(), getName(), getDescription());
        result = 31 * result + Arrays.hashCode(getFileData());
        return result;
    }
}
