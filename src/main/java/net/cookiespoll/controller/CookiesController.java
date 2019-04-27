package net.cookiespoll.controller;

import io.swagger.annotations.*;
import net.cookiespoll.dto.*;
import net.cookiespoll.exception.FileValidationException;
import net.cookiespoll.model.Cookie;
import net.cookiespoll.model.CookieAddingStatus;
import net.cookiespoll.service.CookieService;
import net.cookiespoll.validation.FileValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

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

    @ApiOperation(value = "Add new cookie to store in database", response = AddCookieResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Cookie was successfully added"),
            @ApiResponse(code = 400, message = "Request contains invalid field(s)"),
            @ApiResponse(code = 500, message = "Internal server error"),
    })
    @RequestMapping(value = "/cookies",
            method = RequestMethod.POST)
    @ResponseBody
    public AddCookieResponse addCookie(
                            @RequestPart("file") MultipartFile multipartFile,
                            @Valid @RequestPart("data") AddCookieRequest addCookieRequest
                            ) throws IOException, FileValidationException {
        LOGGER.info("Start processing AddCookieRequest {}", addCookieRequest, multipartFile);
        fileValidator.validate(multipartFile);
        int userId = 1; // temporary decision until getting userId from session will be implemented
        Cookie cookie = cookieService.addCookie(addCookieRequest, multipartFile, userId);

        LOGGER.info("Done");

        return new AddCookieResponse(cookie.getId(),cookie.getName(), cookie.getDescription(), cookie.getFileData(),
                                        cookie.getCookieAddingStatus());

    }

    @RequestMapping(value = "/cookies/lists",
            method = RequestMethod.GET)
    @ResponseBody
    public List<Cookie> getCookiesByParameter (@RequestParam (value="userId", required=false) Integer userId,
                                               @RequestParam (value="id", required=false) Integer id,
                                               @RequestParam (value="name", required = false) String name,
                                               @RequestParam (value="description", required =false) String description,
                                               @RequestParam (value="status", required =false) CookieAddingStatus
                                                           cookieAddingStatus,
                                               @RequestParam (value="rating", required =false) Integer rating) {
       /* TODO if(!cookieService.getUserRole(id).equals(Role.ADMIN))
        { return new ArrayList<Cookie>() ; }*/


            return cookieService.getCookiesByParam(id, name, description,
                    cookieAddingStatus, rating, userId);

    }

    @RequestMapping(value = "/cookies",
            method = RequestMethod.PATCH)
    @ResponseBody
    public UpdateCookieResponse updateCookie (@RequestBody UpdateCookieRequest updateCookieRequest) {
        /* TODO if(!cookieService.getUserRole(id).equals(Role.ADMIN))
        { return new ArrayList<Cookie>() ; }*/

        Cookie cookie = cookieService.updateCookie(updateCookieRequest);
        if (cookie.getCookieAddingStatus().equals(CookieAddingStatus.APPROVED)) {
            /*here will be a method which will add cookie id to table with user id*/
        }
        return new UpdateCookieResponse(updateCookieRequest.getId(),
                updateCookieRequest.getName(), updateCookieRequest.getDescription(),
                updateCookieRequest.getFileData(), updateCookieRequest.getApprovalStatus(),
                updateCookieRequest.getRating(), updateCookieRequest.getUserId());
    }

    @RequestMapping(value = "/cookies/poll",
                    method = RequestMethod.POST)
    @ResponseBody
    public String rateCookie (@RequestBody Cookie cookie) {
        return "ok";
    }
}
