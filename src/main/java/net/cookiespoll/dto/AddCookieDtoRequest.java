package net.cookiespoll.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class AddCookieDtoRequest {

    @NotNull(message = "Cookie name cannot be null")
    @Size(min = 4, max = 30, message = "Cookie name must be between 4 and 30 characters")
    private String name;

    @NotNull(message = "Cookie description cannot be null")
    @Size(min = 1, max = 150, message = "Cookie description must be less then 150 characters and cannot be empty")
    private String description;

    public AddCookieDtoRequest() {}

    public AddCookieDtoRequest(String name, String description) {
        this.name = name;
        this.description = description;
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
}
