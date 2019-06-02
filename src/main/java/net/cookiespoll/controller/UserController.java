package net.cookiespoll.controller;

import net.cookiespoll.model.user.User;
import net.cookiespoll.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:3000"})
@Controller
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    private String getUserIdFromSession() {
        User user = (User) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
       /* Map<String, Object> atrr = defaultOidcUser.getClaims();*/
        return user.getId();
    }

    @RequestMapping(value = "/cookiepoll/login", method = RequestMethod.GET)
    @ResponseBody
    public User getLoginPage () {
        String userId = getUserIdFromSession();

        return userService.getById(userId);
    }

    @RequestMapping(value = "/cookiepoll/logout",
            method = RequestMethod.POST)
    @ResponseBody
    public String logout () {
        return "You are logged out";
    }

    @RequestMapping(value = "/cookiepoll/logout",
            method = RequestMethod.GET)
    @ResponseBody
    public String logout (HttpServletRequest request) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
                securityContextLogoutHandler.logout(request, null, null);
                securityContextLogoutHandler.setClearAuthentication(true);
                securityContextLogoutHandler.setInvalidateHttpSession(true);
        return "You are logged out";
    }

}
