package net.cookiespoll.controller;

import net.cookiespoll.dto.AddCookieDtoRequest;
import net.cookiespoll.service.CookieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@Controller
public class CookiesController {

    @Autowired
    private CookieService cookieService;

    @RequestMapping(value = "/addcookie",
            method = RequestMethod.POST)
    @ResponseBody
    public String addCookie(
            @Valid @ModelAttribute AddCookieDtoRequest addCookieDtoRequest,
            HttpServletResponse response) throws IOException {
        if(!addCookieDtoRequest.getFile().isEmpty()) {
            cookieService.addCookie(addCookieDtoRequest);
        }

        return "file has been uploaded";

    }
}
