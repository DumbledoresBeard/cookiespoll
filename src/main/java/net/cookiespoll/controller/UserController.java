package net.cookiespoll.controller;

import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {


    @RequestMapping(value = "/cookiepoll/login",
            method = RequestMethod.GET)
    @ResponseBody
    public String getLoginPage () {
        return "login in cookies poll";
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
