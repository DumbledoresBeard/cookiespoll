package net.cookiespoll.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import net.cookiespoll.dto.*;
import net.cookiespoll.dto.mapper.CookieDtoMapper;
import net.cookiespoll.exception.CookieRateException;
import net.cookiespoll.exception.FileValidationException;
import net.cookiespoll.model.Cookie;
import net.cookiespoll.model.CookieAddingStatus;
import net.cookiespoll.model.CookieUserRating;
import net.cookiespoll.model.user.User;
import net.cookiespoll.service.CookieService;
import net.cookiespoll.service.CookieUserRatingService;
import net.cookiespoll.service.UserService;
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
import java.util.stream.Collectors;

@Controller
@Api
public class CookiesController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CookiesController.class);

    private CookieService cookieService;
    private FileValidator fileValidator;
    private CookieUserRatingService cookieUserRatingService;
    private CookieDtoMapper cookieDtoMapper;
    private UserService userService;

    @Autowired
    public CookiesController(CookieService cookieService, FileValidator fileValidator,
                             CookieUserRatingService cookieUserRatingService, CookieDtoMapper cookieDtoMapper,
                             UserService userService) {
        this.cookieService = cookieService;
        this.fileValidator = fileValidator;
        this.cookieUserRatingService = cookieUserRatingService;
        this.cookieDtoMapper = cookieDtoMapper;
        this.userService = userService;
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

        return cookieDtoMapper.convertToAddCookieResponse(cookieService.insert(addCookieRequest, multipartFile, user));
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

       return cookieService.getByParam(cookiesByParameterRequest.getName(), cookiesByParameterRequest.getDescription(),
               cookiesByParameterRequest.getCookieAddingStatus(), cookiesByParameterRequest.getRating(),
               cookiesByParameterRequest.getUserId());
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

        return cookieDtoMapper.convertToUpdateResponse(cookieService.update(cookieDtoMapper.convertDto(updateCookieRequest)));
    }


    @ApiOperation(value = "Set rating from user to cookie and count overall cookie rating", response = Cookie.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Cookie got rating and overall rating has been counted"),
            @ApiResponse(code = 400, message = "Request contains invalid field(s)"),
            @ApiResponse(code = 500, message = "Internal server error"),
    })
    @RequestMapping(value = "/cookies/poll", method = RequestMethod.POST)
    @ResponseBody
    public RateCookieResponse rateCookie (@RequestBody @Valid RateCookieRequest rateCookieRequest) throws CookieRateException {
        int userId = 1; // temporary decision until getting userId from session will be implemented

        Cookie cookie = cookieDtoMapper.convertDto(rateCookieRequest);
        User user = userService.getById(userId);

        List<CookieUserRating> cookieUserRatings = user.getRatedCookies();
        for (CookieUserRating cookieUserRating: cookieUserRatings) {
            if (cookieUserRating.getCookie().getCookieId() == cookie.getCookieId()) {
                throw new CookieRateException("This cookie already has been rated by user");
            }
        }

        cookieUserRatings.add(new CookieUserRating(user, cookie, rateCookieRequest.getRating()));
        user.setRatedCookies(cookieUserRatings);

        userService.update(user, new CookieUserRating());

        cookieUserRatingService.setRatingToCookie(userId, cookie.getCookieId(), rateCookieRequest.getRating());

        cookie.setRating(cookieService.countRating(cookie));

        return cookieDtoMapper.convertToRateCookieResponse(cookieService.update(cookie), rateCookieRequest.getRating());

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
        CookieAddingStatus cookieAddingStatus = CookieAddingStatus.APPROVED;

        User user = userService.getById(userId);
        List<Cookie> allApprovedCookies = cookieService.getByParam(null, null, cookieAddingStatus, null,
                null);

        List<Cookie> ratedCookies = user.getRatedCookies()
                                    .stream()
                                    .map(CookieUserRating::getCookie)
                                    .collect(Collectors.toList());
        allApprovedCookies.removeAll(ratedCookies);

        return allApprovedCookies;
    }
}
