package net.cookiespoll.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import net.cookiespoll.dto.*;
import net.cookiespoll.exception.CookieRateException;
import net.cookiespoll.exception.FileValidationException;
import net.cookiespoll.model.Cookie;
import net.cookiespoll.model.user.User;
import net.cookiespoll.service.CookieService;
import net.cookiespoll.service.CookieUserRatingService;
import net.cookiespoll.validation.FileValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@Api
public class CookiesController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CookiesController.class);

    private CookieService cookieService;
    private FileValidator fileValidator;
    private CookieUserRatingService cookieUserRatingService;

    @Autowired
    public CookiesController(CookieService cookieService, FileValidator fileValidator,
                             CookieUserRatingService cookieUserRatingService) {
        this.cookieService = cookieService;
        this.fileValidator = fileValidator;
        this.cookieUserRatingService = cookieUserRatingService;
    }

    @ApiOperation(value = "Add new cookie to store in database", response = AddCookieResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Cookie was successfully added"),
            @ApiResponse(code = 400, message = "Request contains invalid field(s)"),
            @ApiResponse(code = 500, message = "Internal server error"),
    })
    @RequestMapping(value = "/cookies", method = RequestMethod.POST)
    @ResponseBody
    public AddCookieResponse addCookie(@RequestPart("file") MultipartFile multipartFile,
                                       @Valid @RequestPart("data") AddCookieRequest addCookieRequest)
                                        throws IOException, FileValidationException {
        LOGGER.info("Start processing AddCookieRequest {} ", addCookieRequest, multipartFile);

        fileValidator.validate(multipartFile);

        User user = new User();
        user.setId(1); // temporary decision until getting userId from session will be implemented

        Cookie cookie = cookieService.insert(addCookieRequest, multipartFile, user);

        LOGGER.info("Done");

        return new AddCookieResponse(cookie.getId(), cookie.getName(), cookie.getDescription(),
                                    cookie.getFileData(), cookie.getCookieAddingStatus());
    }

    @ApiOperation(value = "Get list of cookies by parameter", response = ArrayList.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Cookies were received"),
            @ApiResponse(code = 400, message = "Request contains invalid field(s)"),
            @ApiResponse(code = 500, message = "Internal server error"),
    })
    @RequestMapping(value = "/cookies/lists", method = RequestMethod.GET)
    @ResponseBody
    public List<Cookie> getCookiesByParameter(@Valid CookiesByParameterRequest cookiesByParameterRequest) {
       /* TODO if(!cookieService.getUserRole(id).equals(Role.ADMIN))
        { return new ArrayList<Cookie>() ; }*/

       LOGGER.info("Starting processing request for getting cookies by parameters {} ", cookiesByParameterRequest);

       return cookieService.getByParam(cookiesByParameterRequest);
    }

    @ApiOperation(value = "Get cookie by id", response = ArrayList.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Cookies were received"),
            @ApiResponse(code = 400, message = "Request contains invalid field(s)"),
            @ApiResponse(code = 500, message = "Internal server error"),
    })
    @RequestMapping(value = "/cookies", method = RequestMethod.GET)
    @ResponseBody
    public Cookie getCookieById (@RequestParam (value = "id") Integer id) {
        LOGGER.info("Starting processing request for getting cookie by id {} ", id);

        return cookieService.getById(id);
    }

    @ApiOperation(value = "Update cookie in database", response = UpdateCookieResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Cookie has been updated"),
            @ApiResponse(code = 400, message = "Request contains invalid field(s)"),
            @ApiResponse(code = 500, message = "Internal server error"),
    })
    @RequestMapping(value = "/cookies", method = RequestMethod.PATCH)
    @ResponseBody
    public UpdateCookieResponse updateCookie (@RequestBody @Valid UpdateCookieRequest updateCookieRequest) {
        /* TODO if(!cookieService.getUserRole(id).equals(Role.ADMIN))
        { return new ArrayList<Cookie>() ; }*/

        LOGGER.info("Starting processing request {} ", updateCookieRequest);

        cookieService.update(new Cookie(updateCookieRequest.getId(), updateCookieRequest.getName(),
                updateCookieRequest.getDescription(), updateCookieRequest.getFileData(),
                updateCookieRequest.getApprovalStatus(), updateCookieRequest.getRating(),
                updateCookieRequest.getCookieOwner()));

        return new UpdateCookieResponse(updateCookieRequest.getId(),
                updateCookieRequest.getName(), updateCookieRequest.getDescription(),
                updateCookieRequest.getFileData(), updateCookieRequest.getApprovalStatus(),
                updateCookieRequest.getRating(), updateCookieRequest.getCookieOwner());
    }


    @ApiOperation(value = "Set rating from user to cookie and count overall cookie rating", response = Cookie.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Cookie got rating and overall rating has been counted"),
            @ApiResponse(code = 400, message = "Request contains invalid field(s)"),
            @ApiResponse(code = 500, message = "Internal server error"),
    })
    @RequestMapping(value = "/cookies/poll", method = RequestMethod.POST)
    @ResponseBody
    public Cookie rateCookie (@RequestBody @Valid RateCookieRequest rateCookieRequest) throws CookieRateException {
        int userId = 1; // temporary decision until getting userId from session will be implemented

        if (cookieUserRatingService.getRatingByUserAndCookie(userId, rateCookieRequest.getId()) != null) {
          throw new CookieRateException("This cookie already has been rated by user");
        }

        cookieUserRatingService.setRatingToCookie(userId, rateCookieRequest.getId(), rateCookieRequest.getRating());

        Float cookieRatingSum = cookieUserRatingService.getRatingSumByCookieId(rateCookieRequest.getId());

        Integer usersQuantity = cookieUserRatingService.getUserQuantity(rateCookieRequest.getId());

        Cookie cookie = new Cookie(rateCookieRequest.getId(), rateCookieRequest.getName(),
                rateCookieRequest.getDescription(), rateCookieRequest.getFileData(),
                rateCookieRequest.getApprovalStatus(), rateCookieRequest.getResultRating(),
                rateCookieRequest.getCookieOwner());

        cookie.setRating(cookieService.countRating(usersQuantity, cookieRatingSum));

        return cookieService.update(cookie);
    }

    @ApiOperation(value = "Get cookies unrated yet by user", response = Cookie.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Cookies were received"),
            @ApiResponse(code = 400, message = "Request contains invalid field(s)"),
            @ApiResponse(code = 500, message = "Internal server error"),
    })
    @RequestMapping(value = "/cookies/poll", method = RequestMethod.GET)
    @ResponseBody
    public List<Cookie> getUnratedCookies () {
        int userId = 1; // temporary decision until getting userId from session will be implemented
        return cookieService.getUnratedCookiesByUserId(userId);
    }
}
