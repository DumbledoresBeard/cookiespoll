package net.cookiespoll.controller;

import net.cookiespoll.dto.AddCookieDtoRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
public class CookiesController {

    @RequestMapping(value = "/addcookie",
            method = RequestMethod.POST)
    @ResponseBody
    public String addCookie(
            @Valid @ModelAttribute AddCookieDtoRequest addCookieDtoRequest,
            HttpServletResponse response) {
        String fileType = addCookieDtoRequest.getFile().getContentType();
        System.out.println(fileType);

        return "file has been uploaded";

    }
}
