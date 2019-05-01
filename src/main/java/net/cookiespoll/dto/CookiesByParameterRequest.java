package net.cookiespoll.dto;

import net.cookiespoll.model.CookieAddingStatus;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

public class CookiesByParameterRequest {

    @Min(value = 1, message = "User id can not be less than 0")
    private Integer userId;

    @Size(min = 4, max = 30, message = "Cookie name must be between 4 and 30 characters")
    private String name;

    @Size(min = 1, max = 150, message = "Cookie description must be less then 150 characters and cannot be empty")
    private String description;


    private CookieAddingStatus cookieAddingStatus;

    @Min(value = 0, message = "Rating can not be less than 0")
    private Float rating;


    public CookiesByParameterRequest(Integer userId, String name, String description,
                                     CookieAddingStatus cookieAddingStatus, Float rating) {
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.cookieAddingStatus = cookieAddingStatus;
        this.rating = rating;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

}
