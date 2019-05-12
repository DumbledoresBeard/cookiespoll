package net.cookiespoll.model;

import net.cookiespoll.model.user.User;


public class CookieUserRating {

    private User user;
    private Cookie cookie;
    private Integer rating;
   /* private int userId;
    private int cookieId;*/

   /* public CookieUserRating(Integer userId, String login, String userName, Role role, Integer rating, Integer cookieId,
                            String name, String description, byte[] fileData, CookieAddingStatus cookieAddingStatus,
                            Float resultRating)
    {
        this.user = new User(userId, login, userName, role);
        this.cookie = new Cookie(cookieId, name, description, fileData, cookieAddingStatus, resultRating);
        this.rating = rating;
    }
*/
    public CookieUserRating() {
    }

    public User getUser() { return user; }

    public void setUser(User user) {
        this.user = user;
    }

    public Cookie getCookie() {
        return cookie;
    }

    public void setCookie(Cookie cookie) {
        this.cookie = cookie;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) { this.rating = rating; }
}
