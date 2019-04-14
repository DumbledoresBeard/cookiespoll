package net.cookiespoll.controller;

import net.cookiespoll.dto.*;
import net.cookiespoll.model.Cookie;
import net.cookiespoll.service.CookieService;
import net.cookiespoll.validation.FileValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@Controller
public class CookiesController {

    @Autowired
    private CookieService cookieService;

    @Autowired
    private FileValidator fileValidator;


    @RequestMapping(value = "/addcookie",
            method = RequestMethod.POST)
    @ResponseBody
    public String addCookie(@RequestHeader HttpHeaders headers,
                            @RequestPart("file") MultipartFile multipartFile, @Valid @RequestPart("data")AddCookieDtoRequest addCookieDtoRequest,
                            HttpServletResponse response) throws IOException, FileAddingException {
        fileValidator.validate(multipartFile);
        Cookie cookie = null;
//        if(!addCookieDtoRequest.getFile().isEmpty()) {
//           cookie = cookieService.addCookie(addCookieDtoRequest);
//        }
//
//        return new AddCookieDtoResponse(cookie.getName(), cookie.getDescription(), cookie.getFileData());
        return "ok";

    }
}
