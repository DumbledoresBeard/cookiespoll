package net.cookiespoll.controller;

import net.cookiespoll.dto.AddCookieDtoRequest;
import net.cookiespoll.dto.AddCookieDtoResponse;
import net.cookiespoll.service.CookieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@Controller
public class CookiesController {

    @Autowired
    private CookieService cookieService;

    @RequestMapping(value = "/addcookie",
            method = RequestMethod.POST)
    @ResponseBody
    public AddCookieDtoResponse addCookie(
            @Valid @ModelAttribute AddCookieDtoRequest addCookieDtoRequest,
            HttpServletResponse response) throws IOException {
        if(!addCookieDtoRequest.getFile().isEmpty()) {
            cookieService.addCookie(addCookieDtoRequest);
        }

        return new AddCookieDtoResponse(addCookieDtoRequest.getName(), addCookieDtoRequest.getDescription(),
                addCookieDtoRequest.getFile());

    }
}
