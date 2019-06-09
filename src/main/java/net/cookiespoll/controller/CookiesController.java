package net.cookiespoll.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import net.cookiespoll.dto.*;
import net.cookiespoll.dto.mapper.CookieDtoConverter;
import net.cookiespoll.exception.CookieRateException;
import net.cookiespoll.exception.FileValidationException;
import net.cookiespoll.exception.UserRoleValidationException;
import net.cookiespoll.model.Cookie;
import net.cookiespoll.model.CookieAddingStatus;
import net.cookiespoll.model.CookieUserRating;
import net.cookiespoll.model.user.User;
import net.cookiespoll.service.CookieService;
import net.cookiespoll.service.UserService;
import net.cookiespoll.validation.FileValidator;
import net.cookiespoll.validation.UserRoleValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import net.cookiespoll.validation.RatingValidator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static net.cookiespoll.utils.UserUtils.getUserFromSession;

@CrossOrigin(origins = {"http://localhost:3000"})
@Controller
@Api
public class CookiesController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CookiesController.class);

    private CookieService cookieService;
    private FileValidator fileValidator;
    private UserRoleValidator userRoleValidator;
    private CookieDtoConverter cookieDtoConverter;
    private UserService userService;
    private RatingValidator ratingValidator;

    @Autowired
    public CookiesController(CookieService cookieService, FileValidator fileValidator, CookieDtoConverter cookieDtoConverter,
                                UserRoleValidator userRoleValidator, UserService userService, RatingValidator ratingValidator) {
        this.cookieService = cookieService;
        this.fileValidator = fileValidator;
        this.userRoleValidator = userRoleValidator;
        this.cookieDtoConverter = cookieDtoConverter;
        this.userService = userService;
        this.ratingValidator = ratingValidator;
    }

    @ApiOperation(value = "Add new cookie to store in database", response = CookieResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Cookie was successfully added"),
            @ApiResponse(code = 400, message = "Request contains invalid field(s)"),
            @ApiResponse(code = 500, message = "Internal server error"),
    })
    @RequestMapping(value = "/cookies", method = RequestMethod.POST)
    @ResponseBody
    public CookieResponse addCookie(@RequestPart("file") MultipartFile multipartFile,
                            @Valid @RequestPart("data") AddCookieRequest addCookieRequest)
                            throws IOException, FileValidationException {
        LOGGER.info("Start processing AddCookieRequest {}, {} ", addCookieRequest, multipartFile);

        fileValidator.validate(multipartFile);

        return cookieDtoConverter.convertToCookieResponse(cookieService.insert(addCookieRequest, multipartFile, getUserFromSession()));
    }

    @ApiOperation(value = "Get list of cookies by parameter", response = ArrayList.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Cookies were received"),
            @ApiResponse(code = 400, message = "Request contains invalid field(s)"),
            @ApiResponse(code = 500, message = "Internal server error"),
    })
    @RequestMapping(value = "/cookies/lists", method = RequestMethod.GET)
    @ResponseBody
    public List<CookieResponse> getCookiesByParameter (@Valid CookiesByParameterRequest cookiesByParameterRequest) {
        LOGGER.info("Starting processing request for getting cookies by parameters {} ", cookiesByParameterRequest);

       return cookieDtoConverter.convertToListOfCookieResponses(cookieService.getByParam(cookiesByParameterRequest.getName(), cookiesByParameterRequest.getDescription(),
               cookiesByParameterRequest.getCookieAddingStatus(), cookiesByParameterRequest.getRating(),
               cookiesByParameterRequest.getUserId()));
    }

    @ApiOperation(value = "Get cookie by id", response = Cookie.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Cookies were received"),
            @ApiResponse(code = 400, message = "Request contains invalid field(s)"),
            @ApiResponse(code = 500, message = "Internal server error"),
    })
    @RequestMapping(value = "/cookies/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Cookie getCookieById (@PathVariable Integer id) {
        LOGGER.info("Starting processing request for getting cookie by id {} ", id);

        return cookieService.getById(id);
    }

    @ApiOperation(value = "Get cookies added by current user", response = ArrayList.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Cookies were received"),
            @ApiResponse(code = 500, message = "Internal server error"),
    })
    @RequestMapping(value = "/cookies", method = RequestMethod.GET)
    @ResponseBody
    public List<CookieResponse> getCookiesAddedByCurrentUser () {
        LOGGER.info("Starting processing request for getting cookie added by current user");

        return cookieDtoConverter.convertToListOfCookieResponses(cookieService.getByParam(null, null, null, null, getUserFromSession().getId()));
    }

    @ApiOperation(value = "Update cookie in database", response = UpdateCookieResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Cookie has been updated"),
            @ApiResponse(code = 400, message = "Request contains invalid field(s)"),
            @ApiResponse(code = 500, message = "Internal server error"),
    })
    @RequestMapping(value = "/cookies", method = RequestMethod.PATCH)
    @ResponseBody
    public UpdateCookieResponse updateCookie (@RequestBody @Valid UpdateCookieRequest updateCookieRequest)
                                                throws UserRoleValidationException {

        if (updateCookieRequest.getApprovalStatus().equals(CookieAddingStatus.APPROVED)
        || updateCookieRequest.getApprovalStatus().equals(CookieAddingStatus.DECLINED))
        {
            userRoleValidator.validateUserRole(getUserFromSession());
        }

        LOGGER.info("Starting processing request {} ", updateCookieRequest);

        return cookieDtoConverter.convertToUpdateResponse(cookieService.update(cookieDtoConverter.convertToCookie(updateCookieRequest)));
    }

    @ApiOperation(value = "Set rating from user to cookie and count overall cookie rating",
            response = RateCookieResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Cookie got rating and overall rating has been counted"),
            @ApiResponse(code = 400, message = "Request contains invalid field(s)"),
            @ApiResponse(code = 500, message = "Internal server error"),
    })
    @RequestMapping(value = "/cookies/poll", method = RequestMethod.POST)
    @ResponseBody
    public RateCookieResponse rateCookie (@RequestBody @Valid RateCookieRequest rateCookieRequest) throws CookieRateException {
        Cookie cookie = cookieService.getById(rateCookieRequest.getId());
        User user = getUserFromSession();
        List<CookieUserRating> cookieUserRatings = user.getRatedCookies();

        ratingValidator.validate(cookieUserRatings, cookie);

        cookieUserRatings.add(new CookieUserRating(user, cookie, rateCookieRequest.getRating()));
        user.setRatedCookies(cookieUserRatings);

        userService.update(user);

        cookie.setRating(cookieService.countRating(cookie));

        return cookieDtoConverter.convertToRateCookieResponse(cookieService.update(cookie), rateCookieRequest.getRating());
    }

    @ApiOperation(value = "Get cookies unrated yet by user", response = ArrayList.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Cookies were received"),
            @ApiResponse(code = 400, message = "Request contains invalid field(s)"),
            @ApiResponse(code = 500, message = "Internal server error"),
    })
    @RequestMapping(value = "/cookies/poll", method = RequestMethod.GET)
    @ResponseBody
    public List<CookieResponse> getUnratedCookies () {
        CookieAddingStatus cookieAddingStatus = CookieAddingStatus.APPROVED;

        List<Cookie> allApprovedCookies = cookieService.getByParam(null, null, cookieAddingStatus, null,
                null);

        List<Cookie> ratedCookies = getUserFromSession().getRatedCookies()
                                    .stream()
                                    .map(CookieUserRating::getCookie)
                                    .collect(Collectors.toList());
        allApprovedCookies.removeAll(ratedCookies);

        return cookieDtoConverter.convertToListOfCookieResponses(allApprovedCookies);
    }

    @ApiOperation(value = "Delete cookie", response = DeleteCookieResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Cookies were received"),
            @ApiResponse(code = 400, message = "Request contains invalid field(s)"),
            @ApiResponse(code = 500, message = "Internal server error"),
    })
    @RequestMapping(value = "/cookies/trash/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public DeleteCookieResponse deleteCookie(@PathVariable ("id") Integer id) throws UserRoleValidationException {
        if(cookieService.getById(id).getCookieAddingStatus().equals(CookieAddingStatus.APPROVED)) {
            userRoleValidator.validateUserRole(getUserFromSession());
        }

        LOGGER.info("Starting processing request {} ", id);

        return new DeleteCookieResponse(cookieService.delete(id));
    }
}
