package net.cookiespoll.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import net.cookiespoll.dto.*;
import net.cookiespoll.dto.mapper.CookieDtoConverter;
import net.cookiespoll.model.Cookie;
import net.cookiespoll.model.CookieAddingStatus;
import net.cookiespoll.model.CookieUserRating;
import net.cookiespoll.model.user.Role;
import net.cookiespoll.model.user.User;
import net.cookiespoll.service.CookieService;
import net.cookiespoll.service.UserService;
import net.cookiespoll.validation.CookieNameUniquenessValidator;
import net.cookiespoll.validation.FileValidator;
import net.cookiespoll.validation.RatingValidator;
import net.cookiespoll.validation.UserRoleValidator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class TestCookieController {

    private MockMvc mockMvc;
    private CookieService cookieService = mock(CookieService.class);
    private FileValidator fileValidator = new FileValidator();
    private UserRoleValidator userRoleValidator = mock(UserRoleValidator.class);
    private CookieDtoConverter dtoConverter = new CookieDtoConverter();
    private RatingValidator ratingValidator = new RatingValidator();
    private CookieNameUniquenessValidator cookieNameUniquenessValidator =
            new CookieNameUniquenessValidator(cookieService);
    private UserService userService = mock(UserService.class);
    private byte [] byteArray = "Photo".getBytes();
    private Float cookieRating = new Float(0);
    private User cookieOwner = new User("1", "login", "name", Role.USER);
    private Cookie cookie = new Cookie(1, "cookie", "tasty cookie", byteArray,
            CookieAddingStatus.WAITING, cookieRating, cookieOwner);
    private Cookie cookieWith1Id = new Cookie(1, "cookie", "tasty cookie",
            new byte[2], CookieAddingStatus.APPROVED, cookieRating, cookieOwner);
    private Cookie cookieWith2Id = new Cookie(2,"name", "description", new byte[2],
            CookieAddingStatus.APPROVED, cookieRating, cookieOwner);
    private MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "testcookie",
            "image/jpg", byteArray);
    private MockMultipartFile addCookieDtoRequest = new MockMultipartFile("data", "",
            "application/json", ("{\"name\":\"cookie\", \"description\": \"tasty cookie\"}").getBytes());
    private Gson gson = new Gson();
    private User user = new User("1", "login", "name", Role.USER);
    private List<CookieUserRating> usersRatings =
            Collections.singletonList(new CookieUserRating(user, cookieWith1Id, 3));
    private List<CookieUserRating> userRatings2 =
            Collections.singletonList(new CookieUserRating(user, cookieWith2Id, 4));

    @Before
    public void init() {
        CookiesController cookiesController = new CookiesController(cookieService, fileValidator,
                dtoConverter, userRoleValidator, userService, ratingValidator, cookieNameUniquenessValidator);

        mockMvc = MockMvcBuilders.standaloneSetup(cookiesController).setControllerAdvice
                  (new ControllerExceptionHandler()).build();

        MockitoAnnotations.initMocks(this);
    }

    private List<Cookie> createCookiesList () {
        List<Cookie> cookies = new ArrayList<>();

        Cookie cookieWith1Id = new Cookie(1, "cookie", "tasty cookie",
                new byte[2], CookieAddingStatus.WAITING, cookieRating, cookieOwner);
        Cookie cookieWith2Id = new Cookie(2,"name", "description",
                new byte[2], CookieAddingStatus.WAITING, cookieRating, cookieOwner);

        cookies.add(cookieWith1Id);
        cookies.add(cookieWith2Id);

        return cookies;
    }

    @WithMockCustomUser
    @Test
    public void testAddCookieValidRequest() throws Exception {
        when(cookieService.insert((any(AddCookieRequest.class)), any(MockMultipartFile.class), any(User.class)))
                .thenReturn(cookie);
        when(userService.getById(anyString())).thenReturn(cookieOwner);
        when(cookieService.getByParam(anyString(), eq(null), eq(null), eq(null),
                eq(null))).thenReturn(List.of());

        String response = mockMvc.perform(MockMvcRequestBuilders.multipart("/cookies")
                .file(mockMultipartFile)
                .file(addCookieDtoRequest)
        ).andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        verify(cookieService).insert((any(AddCookieRequest.class)), any(MockMultipartFile.class), any(User.class));

        CookieResponse cookieResponse = new ObjectMapper().readValue(response, CookieResponse.class);

        Assert.assertEquals(cookie.getName(), cookieResponse.getName());
        Assert.assertEquals(cookie.getDescription(), cookieResponse.getDescription());
        Assert.assertArrayEquals(cookie.getFileData(), cookieResponse.getFileData());
        Assert.assertEquals(cookie.getCookieAddingStatus(), cookieResponse.getCookieAddingStatus());
    }

    @WithMockCustomUser
    @Test
    public void testAddCookieWithNullName() throws Exception {
        MockMultipartFile cookieNullName = new MockMultipartFile("data", "",
                "application/json", "{\"name\": null, \"description\": \"tasty cookie\"}".getBytes());

      mockMvc.perform(MockMvcRequestBuilders.multipart("/cookies")
                .file(mockMultipartFile)
                .file(cookieNullName)
        ).andExpect(status().is(400))
              .andExpect(content().string("{\"errors\":[{\"fieldName\":\"name\"," +
                      "\"message\":\"Cookie name cannot be null\"}]}"));

    }

    @WithMockCustomUser
    @Test
    public void testAddCookieWithTooShortName() throws Exception {
        MockMultipartFile cookieTooShortName = new MockMultipartFile("data", "",
                "application/json", "{\"name\": \"c\", \"description\": \"tasty cookie\"}".getBytes());

        mockMvc.perform(MockMvcRequestBuilders.multipart("/cookies")
                .file(mockMultipartFile)
                .file(cookieTooShortName)
        ).andExpect(status().is(400))
                .andExpect(content().string("{\"errors\":[{\"fieldName\":\"name\"," +
                        "\"message\":\"Cookie name must be between 4 and 30 characters\"}]}"));
    }

    @WithMockCustomUser
    @Test
    public void testAddCookieWithTooLongName() throws Exception {
        MockMultipartFile cookieTooLongName = new MockMultipartFile("data", "",
                "application/json", ("{\"name\": \"tastycookietastycookietastycook\", \"description\": " +
                "\"tasty cookie\"}").getBytes());

        mockMvc.perform(MockMvcRequestBuilders.multipart("/cookies")
                .file(mockMultipartFile)
                .file(cookieTooLongName)
        ).andExpect(status().is(400))
                .andExpect(content().string("{\"errors\":[{\"fieldName\":\"name\"," +
                        "\"message\":\"Cookie name must be between 4 and 30 characters\"}]}"));
    }

    @WithMockCustomUser
    @Test
    public void testAddCookieWithNullDescription() throws Exception {
        MockMultipartFile cookieWithNullDescription = new MockMultipartFile("data", "",
                "application/json", "{\"name\": \"cookie\", \"description\": null}".getBytes());

        mockMvc.perform(MockMvcRequestBuilders.multipart("/cookies")
                .file(mockMultipartFile)
                .file(cookieWithNullDescription)
        ).andExpect(status().is(400))
                .andExpect(content().string("{\"errors\":[{\"fieldName\":\"description\"," +
                        "\"message\":\"Cookie description cannot be null\"}]}"));

    }

    @WithMockCustomUser
    @Test
    public void testAddCookieEmptyDescription () throws Exception {
        MockMultipartFile cookieEmptyDescription = new MockMultipartFile("data", "",
                "application/json", "{\"name\": \"cookie\", \"description\": \"\"}".getBytes());

        mockMvc.perform(MockMvcRequestBuilders.multipart("/cookies")
                .file(mockMultipartFile)
                .file(cookieEmptyDescription)
        ).andExpect(status().is(400))
                .andExpect(content().string("{\"errors\":[{\"fieldName\":\"description\"," +
                        "\"message\":\"Cookie description must be less then 150 characters and cannot be empty\"}]}"));
    }

    @WithMockCustomUser
    @Test
    public void testAddCookieWithTooLongDescription () throws Exception {
        MockMultipartFile cookieWithTooLongDescription = new MockMultipartFile("data", "",
                "application/json", ("{\"name\": \"cookie\", \"description\": " +
                "\"tastycookietastycookietastycookietastycookietastycookietastycookietastycook" +
                "ietastycookietastycookietastycookietastycookietastycookietastycookietastycoo\"}").getBytes());

        mockMvc.perform(MockMvcRequestBuilders.multipart("/cookies")
                .file(mockMultipartFile)
                .file(cookieWithTooLongDescription)
        ).andExpect(status().is(400))
                .andExpect(content().string("{\"errors\":[{\"fieldName\":\"description\"," +
                        "\"message\":\"Cookie description must be less then 150 characters and cannot be empty\"}]}"));
    }

    @WithMockCustomUser
    @Test
    public void testAddCookieEmptyFile() throws Exception {
        MockMultipartFile cookieEmptyFile = new MockMultipartFile("file", "testcookie",
                "text/plain", new byte[0]);

        mockMvc.perform(MockMvcRequestBuilders.multipart("/cookies")
                .file(cookieEmptyFile)
                .file(addCookieDtoRequest)
        ).andExpect(status().is(400))
                .andExpect(content().string("{\"errors\":[{\"fieldName\":\"file\"," +
                        "\"message\":\"File is empty, please, upload jpg, jpeg or png file\"}]}"));
    }

    @WithMockCustomUser
    @Test
    public void testAddCookieInvalidFileType () throws Exception {
        MockMultipartFile cookieInvalidFileType = new MockMultipartFile("file", "testcookie",
                "text/plain", byteArray);

        mockMvc.perform(MockMvcRequestBuilders.multipart("/cookies")
                .file(cookieInvalidFileType)
                .file(addCookieDtoRequest)
        ).andExpect(status().is(400))
                .andExpect(content().string("{\"errors\":[{\"fieldName\":\"file\"," +
                        "\"message\":\"File type is not supported, valid file types: jpg, jpeg or png\"}]}"));
    }

    @Ignore
    @Test
    public void testAddCookieExceededMaxFileSize () throws Exception {
        MockMultipartFile cookieExceededMaxFileSize = new MockMultipartFile("file", "testcookie",
                "image/jpg", new byte[1024 * 1024 * 7]);

        mockMvc.perform(MockMvcRequestBuilders.multipart("/cookies")
                .file(cookieExceededMaxFileSize)
                .file(addCookieDtoRequest)
        ).andExpect(status().is(500));
    }

    @WithMockCustomUser
    @Test
    public void testGetCookiesByParamName() throws Exception {
        List<Cookie> cookies = Arrays.asList(cookieWith1Id, cookieWith2Id);
        String name = "cookie";

        when(cookieService.getByParam(name, null, null, null,
                null)).thenReturn(cookies);

        mockMvc.perform(MockMvcRequestBuilders.get("/cookies/lists")
                .param("name", name)
        ).andExpect(status().isOk())
                .andExpect(content().string("[{\"id\":1,\"name\":\"cookie\",\"description\":\"tasty cookie\",\"fileData\":\"AAA=\",\"cookieAddingStatus\":\"APPROVED\",\"rating\":0.0,\"cookieOwner\":{\"id\":\"1\",\"login\":\"login\",\"name\":\"name\",\"role\":\"USER\"}},{\"id\":2,\"name\":\"name\",\"description\":\"description\",\"fileData\":\"AAA=\",\"cookieAddingStatus\":\"APPROVED\",\"rating\":0.0,\"cookieOwner\":{\"id\":\"1\",\"login\":\"login\",\"name\":\"name\",\"role\":\"USER\"}}]"));

        verify(cookieService).getByParam(name, null, null, null, null);

        System.out.println();
    }

    @WithMockCustomUser
    @Test
    public void testGetCookiesByParamDescription() throws Exception {
        List<Cookie> cookies = createCookiesList();
        String description = "tasty cookie";

        when(cookieService.getByParam(null, description, null, null,
                null)).thenReturn(cookies);

        mockMvc.perform(MockMvcRequestBuilders.get("/cookies/lists")
                .param("description", description)
        ).andExpect(status().isOk())
                .andExpect(content().string("[{\"id\":1,\"name\":\"cookie\",\"description\":\"tasty cookie\",\"fileData\":\"AAA=\",\"cookieAddingStatus\":\"WAITING\",\"rating\":0.0,\"cookieOwner\":{\"id\":\"1\",\"login\":\"login\",\"name\":\"name\",\"role\":\"USER\"}},{\"id\":2,\"name\":\"name\",\"description\":\"description\",\"fileData\":\"AAA=\",\"cookieAddingStatus\":\"WAITING\",\"rating\":0.0,\"cookieOwner\":{\"id\":\"1\",\"login\":\"login\",\"name\":\"name\",\"role\":\"USER\"}}]"));

        verify(cookieService).getByParam(null, description, null, null, null);
    }

    @WithMockCustomUser
    @Test
    public void testGetCookiesByParamCookieAddingStatus() throws Exception {
        List<Cookie> cookies = createCookiesList();
        CookieAddingStatus cookieAddingStatus = CookieAddingStatus.WAITING;

        when(cookieService.getByParam(null, null, cookieAddingStatus, null,
                null)).thenReturn(cookies);

        mockMvc.perform(MockMvcRequestBuilders.get("/cookies/lists")
                .param("cookieAddingStatus", cookieAddingStatus.toString())
        ).andExpect(status().isOk())
                .andExpect(content().string("[{\"id\":1,\"name\":\"cookie\",\"description\":\"tasty cookie\",\"fileData\":\"AAA=\",\"cookieAddingStatus\":\"WAITING\",\"rating\":0.0,\"cookieOwner\":{\"id\":\"1\",\"login\":\"login\",\"name\":\"name\",\"role\":\"USER\"}},{\"id\":2,\"name\":\"name\",\"description\":\"description\",\"fileData\":\"AAA=\",\"cookieAddingStatus\":\"WAITING\",\"rating\":0.0,\"cookieOwner\":{\"id\":\"1\",\"login\":\"login\",\"name\":\"name\",\"role\":\"USER\"}}]"));

        verify(cookieService).getByParam(null, null, cookieAddingStatus, null, null);
    }

    @WithMockCustomUser
    @Test
    public void testGetCookiesByParamRating() throws Exception {
        List<Cookie> cookies = createCookiesList();
        float rating = 0;

        when(cookieService.getByParam(null, null, null, rating,
                null)).thenReturn(cookies);

        mockMvc.perform(MockMvcRequestBuilders.get("/cookies/lists")
                .param("rating", String.valueOf(rating))
        ).andExpect(status().isOk())
                .andExpect(content().string("[{\"id\":1,\"name\":\"cookie\",\"description\":\"tasty cookie\",\"fileData\":\"AAA=\",\"cookieAddingStatus\":\"WAITING\",\"rating\":0.0,\"cookieOwner\":{\"id\":\"1\",\"login\":\"login\",\"name\":\"name\",\"role\":\"USER\"}},{\"id\":2,\"name\":\"name\",\"description\":\"description\",\"fileData\":\"AAA=\",\"cookieAddingStatus\":\"WAITING\",\"rating\":0.0,\"cookieOwner\":{\"id\":\"1\",\"login\":\"login\",\"name\":\"name\",\"role\":\"USER\"}}]"));

        verify(cookieService).getByParam(null, null, null, rating, null);
    }

    @WithMockCustomUser
    @Test
    public void testGetCookiesByParamUserId() throws Exception {
        List<Cookie> cookies = createCookiesList();
        String userId = "1";

        when(cookieService.getByParam(null, null, null, null,
                userId)).thenReturn(cookies);

        mockMvc.perform(MockMvcRequestBuilders.get("/cookies/lists")
               .param("userId", userId.toString())
        ).andExpect(status().isOk())
                .andExpect(content().string("[{\"id\":1,\"name\":\"cookie\",\"description\":\"tasty cookie\",\"fileData\":\"AAA=\",\"cookieAddingStatus\":\"WAITING\",\"rating\":0.0,\"cookieOwner\":{\"id\":\"1\",\"login\":\"login\",\"name\":\"name\",\"role\":\"USER\"}},{\"id\":2,\"name\":\"name\",\"description\":\"description\",\"fileData\":\"AAA=\",\"cookieAddingStatus\":\"WAITING\",\"rating\":0.0,\"cookieOwner\":{\"id\":\"1\",\"login\":\"login\",\"name\":\"name\",\"role\":\"USER\"}}]"));

        verify(cookieService).getByParam(null, null, null, null, userId);
    }

    @WithMockCustomUser
    @Test
    public void testGetCookiesByParamAllParams() throws Exception {
        List<Cookie> cookies = new ArrayList<>();
        Cookie cookieWith1Id = new Cookie(1, "cookie", "tasty cookie", new byte[2], CookieAddingStatus.WAITING,
                cookieRating, cookieOwner);
        cookies.add(cookieWith1Id);
        CookiesByParameterRequest cookiesByParameterRequest = new CookiesByParameterRequest("1","cookie",
                "tasty cookie", CookieAddingStatus.WAITING, cookieRating);
        String name = "cookie";
        String description = "tasty cookie";
        CookieAddingStatus cookieAddingStatus = CookieAddingStatus.WAITING;
        Float rating = new Float(0);
        String userId = "1";

        when(cookieService.getByParam(name, description, cookieAddingStatus, rating, userId)).thenReturn(cookies);

        mockMvc.perform(MockMvcRequestBuilders.get("/cookies/lists")
                .param("name", name)
                .param("description", description)
                .param("cookieAddingStatus", cookieAddingStatus.toString())
                .param("rating", rating.toString())
                .param("userId", userId.toString())
        ).andExpect(status().isOk())
                .andExpect(content().string("[{\"id\":1,\"name\":\"cookie\",\"description\":\"tasty cookie\",\"fileData\":\"AAA=\",\"cookieAddingStatus\":\"WAITING\",\"rating\":0.0,\"cookieOwner\":{\"id\":\"1\",\"login\":\"login\",\"name\":\"name\",\"role\":\"USER\"}}]"));

        verify(cookieService).getByParam(name, description, cookieAddingStatus, rating, userId);
    }

    @WithMockCustomUser
    @Test
    public void testGetCookiesByParamNoParams() throws Exception {
        List<Cookie> cookies = createCookiesList();

        when(cookieService.getByParam(null, null, null, null,
                null)).thenReturn(cookies);

        mockMvc.perform(MockMvcRequestBuilders.get("/cookies/lists")
        ).andExpect(status().isOk())
                .andExpect(content().string("[{\"id\":1,\"name\":\"cookie\",\"description\":\"tasty cookie\",\"fileData\":\"AAA=\",\"cookieAddingStatus\":\"WAITING\",\"rating\":0.0,\"cookieOwner\":{\"id\":\"1\",\"login\":\"login\",\"name\":\"name\",\"role\":\"USER\"}},{\"id\":2,\"name\":\"name\",\"description\":\"description\",\"fileData\":\"AAA=\",\"cookieAddingStatus\":\"WAITING\",\"rating\":0.0,\"cookieOwner\":{\"id\":\"1\",\"login\":\"login\",\"name\":\"name\",\"role\":\"USER\"}}]"));

        verify(cookieService).getByParam(null, null, null, null, null);
    }

    @WithMockCustomUser
    @Test
    public void testGetCookiesByParamTooShortName () throws Exception {
        String name = "n";

        mockMvc.perform(MockMvcRequestBuilders.get("/cookies/lists")
               .param("name", name)
        ).andExpect(status().is(400))
                .andExpect(content().string("{\"errors\":[{\"fieldName\":\"name\"," +
                        "\"message\":\"Cookie name must be between 4 and 30 characters\"}]}"));
    }

    @WithMockCustomUser
    @Test
    public void testGetCookiesByParamTooLongName() throws Exception {
        String name = "tastycookietastycookietastycook";

        mockMvc.perform(MockMvcRequestBuilders.get("/cookies/lists")
                .param("name", name)
        ).andExpect(status().is(400))
                .andExpect(content().string("{\"errors\":[{\"fieldName\":\"name\"," +
                        "\"message\":\"Cookie name must be between 4 and 30 characters\"}]}"));
    }

    @WithMockCustomUser
    @Test
    public void testGetCookiesByParamEmptyDescription () throws Exception {
        String description = "";

        mockMvc.perform(MockMvcRequestBuilders.get("/cookies/lists")
                .param("description", description)
        ).andExpect(status().is(400))
                .andExpect(content().string("{\"errors\":[{\"fieldName\":\"description\"," +
                        "\"message\":\"Cookie description must be less then 150 characters and cannot be empty\"}]}"));
    }

    @WithMockCustomUser
    @Test
    public void testGetCookiesByParamTooLongDescription () throws Exception {
        String description = "tastycookietastycookietastycookietastycookietastycookietastycookietastycook" +
                "ietastycookietastycookietastycookietastycookietastycookietastycookietastycoo";

        mockMvc.perform(MockMvcRequestBuilders.get("/cookies/lists")
              .param("description", description)
        ).andExpect(status().is(400))
                .andExpect(content().string("{\"errors\":[{\"fieldName\":\"description\"," +
                        "\"message\":\"Cookie description must be less then 150 characters and cannot be empty\"}]}"));
    }

    @WithMockCustomUser
    @Test
    public void testGetCookiesByParamInvalidCookieAddingStatus () throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/cookies/lists")
                .param("cookieAddingStatus", "a")
        ).andExpect(status().is(400))
                .andExpect(content().string("{\"errors\":[{\"fieldName\":\"cookieAddingStatus\",\"message\":\"Failed to convert property value of type 'java.lang.String' to required type 'net.cookiespoll.model.CookieAddingStatus' for property 'cookieAddingStatus'; nested exception is org.springframework.core.convert.ConversionFailedException: Failed to convert from type [java.lang.String] to type [net.cookiespoll.model.CookieAddingStatus] for value 'a'; nested exception is java.lang.IllegalArgumentException: No enum constant net.cookiespoll.model.CookieAddingStatus.a\"}]}"));
    }

    @WithMockCustomUser
    @Test
    public void testGetCookiesByParamInvalidRating () throws Exception {
        float rating = -1;

        mockMvc.perform(MockMvcRequestBuilders.get("/cookies/lists")
                .param("rating", String.valueOf(rating))
        ).andExpect(status().is(400))
                .andExpect(content().string("{\"errors\":[{\"fieldName\":\"rating\"," +
                        "\"message\":\"Rating can not be less than 0\"}]}"));
    }

    @WithMockCustomUser
    @Test
    public void testGetCookiesByParamInvalidUserId () throws Exception {
        String userId = "";

        mockMvc.perform(MockMvcRequestBuilders.get("/cookies/lists")
                .param("userId", userId.toString())
        ).andExpect(status().is(400))
                .andExpect(content().string("{\"errors\":[{\"fieldName\":\"userId\"," +
                        "\"message\":\"User id can not be empty string\"}]}"));
    }

    @WithMockCustomUser
    @Test
    public void testGetCookieById() throws Exception {
        Integer id = 1;

        when(cookieService.getById(id)).thenReturn(cookie);


        String response = mockMvc.perform(MockMvcRequestBuilders.get("/cookies/1")
        ).andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        verify(cookieService).getById(id);

        Cookie resultCookie = new ObjectMapper().readValue(response, Cookie.class);

        Assert.assertEquals(cookie.getCookieId(), resultCookie.getCookieId());
        Assert.assertEquals(cookie.getName(), resultCookie.getName());
        Assert.assertEquals(cookie.getDescription(), resultCookie.getDescription());
        Assert.assertArrayEquals(cookie.getFileData(), resultCookie.getFileData());
        Assert.assertEquals(cookie.getCookieAddingStatus(), resultCookie.getCookieAddingStatus());
        Assert.assertEquals(cookie.getRating(), resultCookie.getRating());
        Assert.assertEquals(cookie.getCookieOwner(), resultCookie.getCookieOwner());
    }

    @WithMockCustomUser
    @Test
    public void testUpdateCookie() throws Exception {
        UpdateCookieRequest updateCookieRequest = new UpdateCookieRequest(1, "cookie", "tasty cookie",
                new byte[2], CookieAddingStatus.APPROVED, cookieRating);
        String request = gson.toJson(updateCookieRequest);
        UpdateCookieResponse updateCookieResponse = new UpdateCookieResponse(1, "cookie", "tasty cookie",
                new byte[2], CookieAddingStatus.APPROVED, cookieRating, cookieOwner);

        when(cookieService.update(any(Cookie.class))).thenReturn(cookieWith1Id);

        String response = mockMvc.perform(MockMvcRequestBuilders.patch("/cookies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
        ).andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        verify(cookieService).update(any(Cookie.class));

        UpdateCookieResponse resultResponse = new ObjectMapper().readValue(response, UpdateCookieResponse.class);

        assert updateCookieRequest.getId() == resultResponse.getId();
        Assert.assertEquals(updateCookieResponse.getName(), resultResponse.getName());
        Assert.assertEquals(updateCookieResponse.getDescription(), resultResponse.getDescription());
        Assert.assertArrayEquals(updateCookieResponse.getFileData(), resultResponse.getFileData());
        Assert.assertEquals(updateCookieResponse.getCookieAddingStatus(),
                                    resultResponse.getCookieAddingStatus());
        Assert.assertEquals(updateCookieResponse.getRating(), resultResponse.getRating());
        Assert.assertEquals(cookieWith1Id.getCookieOwner(), resultResponse.getCookieOwner());
    }

    @WithMockCustomUser
    @Test
    public void testRateCookie() throws Exception {
        RateCookieRequest rateCookieRequest = new RateCookieRequest(3,3);
        String request = gson.toJson(rateCookieRequest);
        Cookie cookie = new Cookie(3, "cookie", "tasty cookie", byteArray,
                CookieAddingStatus.APPROVED, cookieRating, cookieOwner);
        List<CookieUserRating> ratedCookies = new ArrayList<CookieUserRating>();
        ratedCookies.add(new CookieUserRating(cookieOwner, cookieWith1Id, 5));
        cookieOwner.setRatedCookies(ratedCookies);
        String userId = "12345";
        CookieOwner cookieOwnerResponse = new CookieOwner(cookieOwner.getId(),cookieOwner.getLogin(), cookieOwner.getName(), cookieOwner.getRole());
        RateCookieResponse rateCookieResponse = new RateCookieResponse(3, "cookie", "tasty cookie",
                byteArray, CookieAddingStatus.APPROVED, (float) 5.5, cookieOwnerResponse, 3);

        when(userService.getById(userId)).thenReturn(cookieOwner);
        when(cookieService.getById(3)).thenReturn(cookie);
        when(userService.update(cookieOwner)).thenReturn(user);
        when(cookieService.countRating(cookie)).thenReturn((float) 5.5);
        cookie.setRating((float) 5.5);
        when(cookieService.update(any(Cookie.class))).thenReturn(cookie);

        String response = mockMvc.perform(MockMvcRequestBuilders.post("/cookies/poll")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
        ).andExpect(status().isOk())
                 .andReturn()
                .getResponse()
                .getContentAsString();

        RateCookieResponse resultResponse = new ObjectMapper().readValue(response, RateCookieResponse.class);
        resultResponse.getCookieOwner();

        assert rateCookieResponse.getId().equals(resultResponse.getId());
        Assert.assertEquals(rateCookieResponse.getName(), resultResponse.getName());
        Assert.assertEquals(rateCookieResponse.getDescription(), resultResponse.getDescription());
        Assert.assertArrayEquals(rateCookieResponse.getFileData(), resultResponse.getFileData());
        Assert.assertEquals(rateCookieResponse.getApprovalStatus(), resultResponse.getApprovalStatus());
        Assert.assertEquals(rateCookieResponse.getOverallRating(), resultResponse.getOverallRating());
        Assert.assertEquals(rateCookieResponse.getCookieOwner(), resultResponse.getCookieOwner());
        assert rateCookieResponse.getRatingGivenByUser().equals(resultResponse.getRatingGivenByUser());
    }

    @WithMockCustomUser
    @Test
    public void testRateAlreadyRatedCookie() throws Exception {
        RateCookieRequest rateCookieRequest = new RateCookieRequest(1, 3);
        cookieOwner.setRatedCookies(usersRatings);
        List<Cookie> addedCookies = new ArrayList<>();
        addedCookies.add(cookieWith1Id);
        cookieOwner.setAddedCookies(addedCookies);
        String request = new ObjectMapper().writeValueAsString(rateCookieRequest);

        when(cookieService.getById(1)).thenReturn(cookieWith1Id);

        mockMvc.perform(MockMvcRequestBuilders.post("/cookies/poll")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
        ).andExpect(status().is(400))
                .andExpect(content().string("{\"errors\":[{\"fieldName\":\"NO FIELD\",\"message\":\"This cookie already has been rated by user\"}]}"));

        verify(cookieService).getById(1);
    }

    @WithMockCustomUser
    @Test
    public void testGetUnratedCookie() throws Exception {
        List<Cookie> addedCookies = new ArrayList<>();
        addedCookies.add(cookieWith1Id);
        cookieWith1Id.getCookieOwner().setRatedCookies(usersRatings);
        cookieWith1Id.getCookieOwner().setAddedCookies(addedCookies);
        List<Cookie> approvedCookies = new ArrayList<>();
        approvedCookies.add(cookieWith1Id);
        approvedCookies.add(cookieWith2Id);

        when(cookieService.getByParam(null, null, CookieAddingStatus.APPROVED, null,
                null)).thenReturn(approvedCookies);

        mockMvc.perform(MockMvcRequestBuilders.get("/cookies/poll")
        ).andExpect(status().isOk())
                .andExpect(content().string("[{\"id\":2,\"name\":\"name\",\"description\":\"description\",\"fileData\":\"AAA=\",\"cookieAddingStatus\":\"APPROVED\",\"rating\":0.0,\"cookieOwner\":{\"id\":\"1\",\"login\":\"login\",\"name\":\"name\",\"role\":\"USER\"}}]"));

        verify(cookieService).getByParam(null, null, CookieAddingStatus.APPROVED, null, null);
    }

    @WithMockCustomUser
    @Test
    public void testGetCookiesAddedByCurrentUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/cookies")
        ).andExpect(status().isOk())
                .andExpect(content().string("[{\"id\":1,\"name\":\"cookie\",\"description\":\"tasty cookie\",\"fileData\":\"AAA=\",\"cookieAddingStatus\":\"APPROVED\",\"rating\":0.0,\"cookieOwner\":{\"id\":\"1\",\"login\":\"login\",\"name\":\"name\",\"role\":\"USER\"}}]"));
    }

    @WithMockCustomUser
    @Test
    public void testDeleteCookie() throws Exception {
        Integer id = 1;
        DeleteCookieResponse deleteCookieResponse = new DeleteCookieResponse(id);

        when(cookieService.delete(id)).thenReturn(id);
        when(cookieService.getById(id)).thenReturn(cookie);

        String response = mockMvc.perform(MockMvcRequestBuilders.delete("/cookies/trash/1")
        ).andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        verify(cookieService).delete(id);
        verify(cookieService).getById(id);

        new ObjectMapper().readValue(response, DeleteCookieResponse.class);

        Assert.assertEquals(id, deleteCookieResponse.getCookieId());
    }
}


