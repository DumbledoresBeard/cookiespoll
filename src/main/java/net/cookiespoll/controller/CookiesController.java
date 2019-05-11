package net.cookiespoll.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import net.cookiespoll.dto.*;
import net.cookiespoll.dto.mapper.DtoMapper;
import net.cookiespoll.exception.CookieRateException;
import net.cookiespoll.exception.FileValidationException;
import net.cookiespoll.exception.UserRoleValidationException;
import net.cookiespoll.model.Cookie;
import net.cookiespoll.model.CookieAddingStatus;
import net.cookiespoll.model.user.User;
import net.cookiespoll.service.CookieService;
import net.cookiespoll.service.CookieUserRatingService;
import net.cookiespoll.validation.FileValidator;
import net.cookiespoll.validation.UserRoleValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@Api
public class CookiesController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CookiesController.class);

    private CookieService cookieService;
    private FileValidator fileValidator;
    private CookieUserRatingService cookieUserRatingService;
    private UserRoleValidator userRoleValidator;
    private DtoMapper dtoMapper;

    @Autowired
    public CookiesController(CookieService cookieService, FileValidator fileValidator,
                             CookieUserRatingService cookieUserRatingService, DtoMapper dtoMapper,
                             UserRoleValidator userRoleValidator) {
        this.cookieService = cookieService;
        this.fileValidator = fileValidator;
        this.cookieUserRatingService = cookieUserRatingService;
        this.dtoMapper = dtoMapper;
        this.userRoleValidator = userRoleValidator;
    }

    private String getUserIdFromSession() {
        DefaultOidcUser defaultOidcUser = (DefaultOidcUser) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        Map<String, Object> atrr = defaultOidcUser.getClaims();
        return (String) atrr.get("sub");
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

        String userId = getUserIdFromSession();
        User user = new User();

        Cookie cookie = cookieService.insert(addCookieRequest, multipartFile, user);

        fileValidator.validate(multipartFile);

        return dtoMapper.convertCookieToAddCookieResponse(cookieService.insert(addCookieRequest, multipartFile, user));
    }

    @ApiOperation(value = "Get list of cookies by parameter", response = ArrayList.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Cookies were received"),
            @ApiResponse(code = 400, message = "Request contains invalid field(s)"),
            @ApiResponse(code = 500, message = "Internal server error"),
    })
    @RequestMapping(value = "/cookies/lists", method = RequestMethod.GET)
    @ResponseBody
    public List<Cookie> getCookiesByParameter (@Valid CookiesByParameterRequest cookiesByParameterRequest) {
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
        String userId = getUserIdFromSession();
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
    public UpdateCookieResponse updateCookie (@RequestBody @Valid UpdateCookieRequest updateCookieRequest)
                                                throws UserRoleValidationException {

        if (updateCookieRequest.getApprovalStatus().equals(CookieAddingStatus.APPROVED)
        || updateCookieRequest.getApprovalStatus().equals(CookieAddingStatus.DECLINED))
        {
            userRoleValidator.validateUserRole(getUserIdFromSession());
        }

        LOGGER.info("Starting processing request {} ", updateCookieRequest);

        return dtoMapper.convertCookieToUpdateResponse(cookieService.update(dtoMapper.convertDtoToCookie(updateCookieRequest)));
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
        String userId = getUserIdFromSession();

        if (cookieUserRatingService.getRatingByUserAndCookie(userId, rateCookieRequest.getId()) != null) {
            throw new CookieRateException("This cookie already has been rated by user");
        }

        cookieUserRatingService.setRatingToCookie(userId, rateCookieRequest.getId(), rateCookieRequest.getRating());

        Float cookieRatingSum = cookieUserRatingService.getRatingSumByCookieId(rateCookieRequest.getId());

        Integer usersQuantity = cookieUserRatingService.getUserQuantity(rateCookieRequest.getId());

        Cookie cookie = dtoMapper.convertDtoToCookie(rateCookieRequest);

        cookie.setRating(cookieService.countRating(usersQuantity, cookieRatingSum));

        return new RateCookieResponse(cookieService.update(cookie), rateCookieRequest.getRating());
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
        String userId = getUserIdFromSession();

        return cookieService.getUnratedByUserId(userId);
    }

    @RequestMapping(value = "/cookies", method = RequestMethod.DELETE)
    @ResponseBody
    public DeleteCookieResponse deleteCookie(@PathVariable ("id") Integer id) throws UserRoleValidationException {
        if(cookieService.getById(id).getCookieAddingStatus().equals(CookieAddingStatus.APPROVED)) {
            userRoleValidator.validateUserRole(getUserIdFromSession());
        }

        LOGGER.info("Starting processing request {} ", id);

        return new DeleteCookieResponse(cookieService.delete(id));
    }
}
