package net.cookiespoll.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class RateCookieRequest {
    private Integer id;

    @Min(value = 1, message = "Rating must be a digit between 1 and 5")
    @Max(value = 5, message = "Rating must be a digit between 1 and 5")
    private Integer rating;

    public RateCookieRequest() {
    }

    public RateCookieRequest(Integer id, Integer rating) {
        this.id = id;
        this.rating = rating;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
