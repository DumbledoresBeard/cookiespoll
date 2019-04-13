package net.cookiespoll.dto;

import net.cookiespoll.validation.FileConstraint;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.Email;


public class AddCookieDtoRequest {

    @NotNull(message = "Cookie name cannot be null")
    @Size(min = 4, max = 30, message = "Cookie name must be between 4 and 30 characters")
    private String name;

    @NotNull(message = "Cookie description cannot be null")
    @Size(max = 150, message = "Cookie description must be less then 150 characters")
    private String description;

   /* @NotNull(message = "Cookie photo cannot be null")
    @FileConstraint
    private MultipartFile file;

    public AddCookieDtoRequest () {}

    public AddCookieDtoRequest(@NotNull(message = "Cookie name cannot be null") @Size(min = 4, max = 30, message = "Cookie name must be between 4 and 30 characters") String name, @NotNull(message = "Cookie description cannot be null") @Size(max = 150, message = "Cookie description must be less then 150 characters") String description, @NotNull(message = "Cookie photo cannot be null") MultipartFile file) {
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
    }*/

    public AddCookieDtoRequest() {
    }

    public AddCookieDtoRequest(@NotNull(message = "Cookie name cannot be null") @Size(min = 4, max = 30, message = "Cookie name must be between 4 and 30 characters") String name, @NotNull(message = "Cookie description cannot be null") @Size(max = 150, message = "Cookie description must be less then 150 characters") String description) {
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
