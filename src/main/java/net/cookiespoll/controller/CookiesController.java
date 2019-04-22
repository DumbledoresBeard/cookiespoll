package net.cookiespoll.controller;

import io.swagger.annotations.*;
import net.cookiespoll.dto.*;
import net.cookiespoll.exception.FileValidationException;
import net.cookiespoll.model.Cookie;
import net.cookiespoll.service.CookieService;
import net.cookiespoll.validation.FileValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.Valid;
import java.io.IOException;

@Controller
@Api(description = "Operations related to cookie adding")
public class CookiesController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CookiesController.class);

    private CookieService cookieService;
    private FileValidator fileValidator;

    @Autowired
    public CookiesController(CookieService cookieService, FileValidator fileValidator) {
        this.cookieService = cookieService;
        this.fileValidator = fileValidator;
    }

    @ApiOperation(value = "Add new cookie to store in database", response = AddCookieDtoResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Cookie was successfully added"),
            @ApiResponse(code = 400, message = "Request contains invalid field(s)"),
            @ApiResponse(code = 500, message = "Internal server error"),
    })
    @RequestMapping(value = "/addcookie",
            method = RequestMethod.POST)
    @ResponseBody
    public AddCookieDtoResponse addCookie(
                            @RequestPart("file") MultipartFile multipartFile,
                            @Valid @RequestPart("data") AddCookieDtoRequest addCookieDtoRequest
                            ) throws IOException, FileValidationException {
        LOGGER.info("Start processing AddCookieDtoRequest {}", addCookieDtoRequest, multipartFile);
        fileValidator.validate(multipartFile);

        Cookie cookie = cookieService.addCookie(addCookieDtoRequest, multipartFile);

        LOGGER.info("Done");

        return new AddCookieDtoResponse(cookie.getName(), cookie.getDescription(), cookie.getFileData(),
                                        cookie.getCookieAddingStatus());

    }
}
