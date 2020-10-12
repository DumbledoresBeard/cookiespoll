package net.cookiespoll.controller;

import net.cookiespoll.model.user.User;
import net.cookiespoll.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static net.cookiespoll.utils.UserUtils.getUserFromSession;

@CrossOrigin(origins = {"http://localhost:3000"})
@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/cookiepoll/user", method = RequestMethod.GET)
    @ResponseBody
    public User getCurrentUser() {
        return getUserFromSession();
    }

    @RequestMapping(value = "/cookiepoll/user/{id}", method = RequestMethod.GET)
    @ResponseBody
    public User getUser(@PathVariable String id) {
        return userService.getById(id);
    }

    @RequestMapping(value = "/cookiepoll/logout",
            method = RequestMethod.GET)
    @ResponseBody
    public void logout (HttpServletRequest request) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
                securityContextLogoutHandler.logout(request, null, null);
                securityContextLogoutHandler.setClearAuthentication(true);
                securityContextLogoutHandler.setInvalidateHttpSession(true);
    }

}
