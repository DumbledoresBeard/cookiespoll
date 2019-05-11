package net.cookiespoll.dto;

public class DeleteCookieResponse {

    Integer cookieId;

    public DeleteCookieResponse() {}

    public DeleteCookieResponse(Integer cookieId) {
        this.cookieId = cookieId;
    }

    public Integer getCookieId() {
        return cookieId;
    }

    public void setCookieId(Integer cookieId) {
        this.cookieId = cookieId;
    }
}
