package net.cookiespoll.controller;

import net.cookiespoll.dto.AddCookieDtoRequest;
import net.cookiespoll.dto.AddCookieDtoResponse;
import net.cookiespoll.model.Cookie;
import net.cookiespoll.service.CookieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


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
    public String addCookie(@RequestHeader HttpHeaders headers,
            @Valid @RequestPart MultipartFile file, @Valid @RequestPart("data")AddCookieDtoRequest addCookieDtoRequest,
            HttpServletResponse response) throws IOException {
        Cookie cookie = null;
//        if(!addCookieDtoRequest.getFile().isEmpty()) {
//           cookie = cookieService.addCookie(addCookieDtoRequest);
//        }
//
//        return new AddCookieDtoResponse(cookie.getName(), cookie.getDescription(), cookie.getFileData());
        return "ok";

    }
}
