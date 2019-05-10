package net.cookiespoll.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import net.cookiespoll.dto.*;
import net.cookiespoll.model.Cookie;
import net.cookiespoll.model.CookieAddingStatus;
import net.cookiespoll.model.user.Role;
import net.cookiespoll.model.user.User;
import net.cookiespoll.service.CookieService;
import net.cookiespoll.validation.FileValidator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

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
    private CookiesController cookiesController;
    private byte [] byteArray = "Photo".getBytes();
    private User cookieOwner = new User(1, "login", "name", Role.USER);
    private Cookie cookie = new Cookie(1, "cookie", "tasty cookie", byteArray,
            CookieAddingStatus.WAITING, 0, cookieOwner);
    private MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "testcookie",
            "image/jpg", byteArray);
    private MockMultipartFile addCookieDtoRequest = new MockMultipartFile("data", "",
            "application/json", ("{\"name\":\"cookie\", \"description\": \"tasty cookie\"}").getBytes());
    private Gson gson = new Gson();


    @Before
    public void init() {
        cookiesController = new CookiesController(cookieService, fileValidator);
        mockMvc = MockMvcBuilders.standaloneSetup(cookiesController).setControllerAdvice
                  (new ControllerExceptionHandler()).build();
        MockitoAnnotations.initMocks(this);
    }

    private List<Cookie> createCookiesList () {
        List<Cookie> cookies = new ArrayList<>();
        Cookie cookieWith1Id = new Cookie(1, "cookie", "tasty cookie", new byte[2],
                CookieAddingStatus.WAITING, 0, cookieOwner);
        Cookie cookieWith2Id = new Cookie(2,"name", "description", new byte[2],
                CookieAddingStatus.WAITING, 0, cookieOwner);
        cookies.add(cookieWith1Id);
        cookies.add(cookieWith2Id);
        return cookies;
    }

    @Test
    public void testAddCookieValidRequest() throws Exception {
        when(cookieService.insert((any(AddCookieRequest.class)), any(MockMultipartFile.class), any(User.class)))
                .thenReturn(cookie);

        String response = mockMvc.perform(MockMvcRequestBuilders.multipart("/cookies")
                .file(mockMultipartFile)
                .file(addCookieDtoRequest)
        ).andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        verify(cookieService).insert((any(AddCookieRequest.class)), any(MockMultipartFile.class), any(User.class));

        AddCookieResponse addCookieResponse = new ObjectMapper().readValue(response, AddCookieResponse.class);

        Assert.assertEquals(cookie.getName(), addCookieResponse.getName());
        Assert.assertEquals(cookie.getDescription(), addCookieResponse.getDescription());
        Assert.assertArrayEquals(cookie.getFileData(), addCookieResponse.getFileData());
        Assert.assertEquals(cookie.getCookieAddingStatus(), addCookieResponse.getCookieAddingStatus());

    }


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
    /*            .andExpect((ResultMatcher) jsonPath("$.message", is("Maximum upload size exceeded; nested exception is java.lang.IllegalStateException: org.apache.tomcat.util.http.fileupload.FileUploadBase$SizeLimitExceededException: the request was rejected because its size (8055342) exceeds the configured maximum (5242880)")));*/

    }


    @Test
    public void testGetCookiesByParamName() throws Exception {
        List<Cookie> cookies = createCookiesList();
        String name = "cookie";

        when(cookieService.getByParam(any(CookiesByParameterRequest.class))).thenReturn(cookies);

        mockMvc.perform(MockMvcRequestBuilders.get("/cookies/lists")
                .param("name", name)
        ).andExpect(status().isOk())
                .andExpect(content().string("[{\"id\":1,\"name\":\"cookie\",\"description\":\"tasty cookie\",\"fileData\":\"AAA=\",\"cookieAddingStatus\":\"WAITING\",\"rating\":0,\"cookieOwner\":{\"id\":1,\"login\":\"login\",\"name\":\"name\",\"role\":\"USER\"}},{\"id\":2,\"name\":\"name\",\"description\":\"description\",\"fileData\":\"AAA=\",\"cookieAddingStatus\":\"WAITING\",\"rating\":0,\"cookieOwner\":{\"id\":1,\"login\":\"login\",\"name\":\"name\",\"role\":\"USER\"}}]"));

        verify(cookieService).getByParam(any(CookiesByParameterRequest.class));

        System.out.println();

    }

    @Test
    public void testGetCookiesByParamDescription() throws Exception {
        List<Cookie> cookies = createCookiesList();
        String description = "tasty cookie";

        when(cookieService.getByParam(any(CookiesByParameterRequest.class))).thenReturn(cookies);

        mockMvc.perform(MockMvcRequestBuilders.get("/cookies/lists")
                .param("description", description)
        ).andExpect(status().isOk())
                .andExpect(content().string("[{\"id\":1,\"name\":\"cookie\",\"description\":\"tasty cookie\",\"fileData\":\"AAA=\",\"cookieAddingStatus\":\"WAITING\",\"rating\":0,\"cookieOwner\":{\"id\":1,\"login\":\"login\",\"name\":\"name\",\"role\":\"USER\"}},{\"id\":2,\"name\":\"name\",\"description\":\"description\",\"fileData\":\"AAA=\",\"cookieAddingStatus\":\"WAITING\",\"rating\":0,\"cookieOwner\":{\"id\":1,\"login\":\"login\",\"name\":\"name\",\"role\":\"USER\"}}]"));

        verify(cookieService).getByParam(any(CookiesByParameterRequest.class));
    }

    @Test
    public void testGetCookiesByParamCookieAddingStatus() throws Exception {
        List<Cookie> cookies = createCookiesList();
        CookieAddingStatus cookieAddingStatus = CookieAddingStatus.WAITING;

        when(cookieService.getByParam(any(CookiesByParameterRequest.class))).thenReturn(cookies);

        mockMvc.perform(MockMvcRequestBuilders.get("/cookies/lists")
                .param("cookieAddingStatus", cookieAddingStatus.toString())
        ).andExpect(status().isOk())
                .andExpect(content().string("[{\"id\":1,\"name\":\"cookie\",\"description\":\"tasty cookie\",\"fileData\":\"AAA=\",\"cookieAddingStatus\":\"WAITING\",\"rating\":0,\"cookieOwner\":{\"id\":1,\"login\":\"login\",\"name\":\"name\",\"role\":\"USER\"}},{\"id\":2,\"name\":\"name\",\"description\":\"description\",\"fileData\":\"AAA=\",\"cookieAddingStatus\":\"WAITING\",\"rating\":0,\"cookieOwner\":{\"id\":1,\"login\":\"login\",\"name\":\"name\",\"role\":\"USER\"}}]"));

        verify(cookieService).getByParam(any(CookiesByParameterRequest.class));
    }

    @Test
    public void testGetCookiesByParamRating() throws Exception {
        List<Cookie> cookies = createCookiesList();
        Integer rating = 0;

        when(cookieService.getByParam(any(CookiesByParameterRequest.class))).thenReturn(cookies);

        mockMvc.perform(MockMvcRequestBuilders.get("/cookies/lists")
                .param("rating", rating.toString())
        ).andExpect(status().isOk())
                .andExpect(content().string("[{\"id\":1,\"name\":\"cookie\",\"description\":\"tasty cookie\",\"fileData\":\"AAA=\",\"cookieAddingStatus\":\"WAITING\",\"rating\":0,\"cookieOwner\":{\"id\":1,\"login\":\"login\",\"name\":\"name\",\"role\":\"USER\"}},{\"id\":2,\"name\":\"name\",\"description\":\"description\",\"fileData\":\"AAA=\",\"cookieAddingStatus\":\"WAITING\",\"rating\":0,\"cookieOwner\":{\"id\":1,\"login\":\"login\",\"name\":\"name\",\"role\":\"USER\"}}]"));

        verify(cookieService).getByParam(any(CookiesByParameterRequest.class));
    }

    @Test
    public void testGetCookiesByParamUserId() throws Exception {
        List<Cookie> cookies = createCookiesList();
        Integer userId = 1;

        when(cookieService.getByParam(any(CookiesByParameterRequest.class))).thenReturn(cookies);

        mockMvc.perform(MockMvcRequestBuilders.get("/cookies/lists")
               .param("userId", userId.toString())
        ).andExpect(status().isOk())
                .andExpect(content().string("[{\"id\":1,\"name\":\"cookie\",\"description\":\"tasty cookie\",\"fileData\":\"AAA=\",\"cookieAddingStatus\":\"WAITING\",\"rating\":0,\"cookieOwner\":{\"id\":1,\"login\":\"login\",\"name\":\"name\",\"role\":\"USER\"}},{\"id\":2,\"name\":\"name\",\"description\":\"description\",\"fileData\":\"AAA=\",\"cookieAddingStatus\":\"WAITING\",\"rating\":0,\"cookieOwner\":{\"id\":1,\"login\":\"login\",\"name\":\"name\",\"role\":\"USER\"}}]"));

        verify(cookieService).getByParam(any(CookiesByParameterRequest.class));
    }

    @Test
    public void testGetCookiesByParamAllParams() throws Exception {
        List<Cookie> cookies = new ArrayList<>();
        Cookie cookieWith1Id = new Cookie(1, "cookie", "tasty cookie", new byte[2],
                CookieAddingStatus.WAITING, 0, cookieOwner);
        cookies.add(cookieWith1Id);
        CookiesByParameterRequest cookiesByParameterRequest = new CookiesByParameterRequest(1,"cookie",
                "tasty cookie", CookieAddingStatus.WAITING, 0 );

        when(cookieService.getByParam(any(CookiesByParameterRequest.class)))
                .thenReturn(cookies);

        mockMvc.perform(MockMvcRequestBuilders.get("/cookies/lists")
               .param("name", cookiesByParameterRequest.getName())
                .param("description", cookiesByParameterRequest.getDescription())
                .param("cookieAddingStatus", cookiesByParameterRequest.getCookieAddingStatus().toString())
                .param("rating", cookiesByParameterRequest.getRating().toString())
                .param("userId", cookiesByParameterRequest.getUserId().toString())
        ).andExpect(status().isOk())
                .andExpect(content().string("[{\"id\":1,\"name\":\"cookie\",\"description\":\"tasty cookie\",\"fileData\":\"AAA=\",\"cookieAddingStatus\":\"WAITING\",\"rating\":0,\"cookieOwner\":{\"id\":1,\"login\":\"login\",\"name\":\"name\",\"role\":\"USER\"}}]"));

        verify(cookieService).getByParam(any(CookiesByParameterRequest.class));
    }

    @Test
    public void testGetCookiesByParamNoParams() throws Exception {
        List<Cookie> cookies = createCookiesList();

        when(cookieService.getByParam(any(CookiesByParameterRequest.class))).thenReturn(cookies);

        mockMvc.perform(MockMvcRequestBuilders.get("/cookies/lists")
        ).andExpect(status().isOk())
                .andExpect(content().string("[{\"id\":1,\"name\":\"cookie\",\"description\":\"tasty cookie\",\"fileData\":\"AAA=\",\"cookieAddingStatus\":\"WAITING\",\"rating\":0,\"cookieOwner\":{\"id\":1,\"login\":\"login\",\"name\":\"name\",\"role\":\"USER\"}},{\"id\":2,\"name\":\"name\",\"description\":\"description\",\"fileData\":\"AAA=\",\"cookieAddingStatus\":\"WAITING\",\"rating\":0,\"cookieOwner\":{\"id\":1,\"login\":\"login\",\"name\":\"name\",\"role\":\"USER\"}}]"));

        verify(cookieService).getByParam(any(CookiesByParameterRequest.class));
    }

    @Test
    public void testGetCookiesByParamTooShortName () throws Exception {
        String name = "n";

        mockMvc.perform(MockMvcRequestBuilders.get("/cookies/lists")
               .param("name", name)
        ).andExpect(status().is(400))
                .andExpect(content().string("{\"errors\":[{\"fieldName\":\"name\"," +
                        "\"message\":\"Cookie name must be between 4 and 30 characters\"}]}"));
    }

    @Test
    public void testGetCookiesByParamTooLongName() throws Exception {
        String name = "tastycookietastycookietastycook";

        mockMvc.perform(MockMvcRequestBuilders.get("/cookies/lists")
                .param("name", name)
        ).andExpect(status().is(400))
                .andExpect(content().string("{\"errors\":[{\"fieldName\":\"name\"," +
                        "\"message\":\"Cookie name must be between 4 and 30 characters\"}]}"));
    }

    @Test
    public void testGetCookiesByParamEmptyDescription () throws Exception {
        String description = "";

        mockMvc.perform(MockMvcRequestBuilders.get("/cookies/lists")
                .param("description", description)
        ).andExpect(status().is(400))
                .andExpect(content().string("{\"errors\":[{\"fieldName\":\"description\"," +
                        "\"message\":\"Cookie description must be less then 150 characters and cannot be empty\"}]}"));
    }

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

    @Test
    public void testGetCookiesByParamInvalidCookieAddingStatus () throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/cookies/lists")
                .param("cookieAddingStatus", "a")
        ).andExpect(status().is(400))
                .andExpect(content().string("{\"errors\":[{\"fieldName\":\"cookieAddingStatus\",\"message\":\"Failed to convert property value of type 'java.lang.String' to required type 'net.cookiespoll.model.CookieAddingStatus' for property 'cookieAddingStatus'; nested exception is org.springframework.core.convert.ConversionFailedException: Failed to convert from type [java.lang.String] to type [net.cookiespoll.model.CookieAddingStatus] for value 'a'; nested exception is java.lang.IllegalArgumentException: No enum constant net.cookiespoll.model.CookieAddingStatus.a\"}]}"));
    }

    @Test
    public void testGetCookiesByParamInvalidRating () throws Exception {
        Integer rating = -1;

        mockMvc.perform(MockMvcRequestBuilders.get("/cookies/lists")
                .param("rating", rating.toString())
        ).andExpect(status().is(400))
                .andExpect(content().string("{\"errors\":[{\"fieldName\":\"rating\"," +
                        "\"message\":\"Rating can not be less than 0\"}]}"));
    }

    @Test
    public void testGetCookiesByParamInvalidUserId () throws Exception {
        Integer userId = -1;

        mockMvc.perform(MockMvcRequestBuilders.get("/cookies/lists")
                .param("userId", userId.toString())
        ).andExpect(status().is(400))
                .andExpect(content().string("{\"errors\":[{\"fieldName\":\"userId\"," +
                        "\"message\":\"User id can not be less than 0\"}]}"));
    }

    @Test
    public void testGetCookieById () throws Exception {
        Integer id = 1;

        when(cookieService.getById(id)).thenReturn(cookie);


        String response = mockMvc.perform(MockMvcRequestBuilders.get("/cookies")
                .param("id", String.valueOf(id))
        ).andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        verify(cookieService).getById(id);

        Cookie resultCookie = new ObjectMapper().readValue(response, Cookie.class);

        Assert.assertEquals(cookie.getId(), resultCookie.getId());
        Assert.assertEquals(cookie.getName(), resultCookie.getName());
        Assert.assertEquals(cookie.getDescription(), resultCookie.getDescription());
        Assert.assertArrayEquals(cookie.getFileData(), resultCookie.getFileData());
        Assert.assertEquals(cookie.getCookieAddingStatus(), resultCookie.getCookieAddingStatus());
        Assert.assertEquals(cookie.getRating(), resultCookie.getRating());
        Assert.assertEquals(cookie.getCookieOwner(), resultCookie.getCookieOwner());
    }


    @Test
    public void testUpdateCookie () throws Exception {
        UpdateCookieRequest updateCookieRequest = new UpdateCookieRequest(
                1, "cookie", "tasty cookie", byteArray,
                CookieAddingStatus.APPROVED, 0, cookieOwner);
        String request = gson.toJson(updateCookieRequest);
        UpdateCookieResponse updateCookieResponse = new UpdateCookieResponse(
                1, "cookie", "tasty cookie", byteArray,
                CookieAddingStatus.APPROVED, 0, cookieOwner);

        when(cookieService.update(any(UpdateCookieRequest.class))).thenReturn(cookie);

        String response = mockMvc.perform(MockMvcRequestBuilders.patch("/cookies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
        ).andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        verify(cookieService).update(any(UpdateCookieRequest.class));

        UpdateCookieResponse resultResponse = new ObjectMapper().readValue(response,
                                                            UpdateCookieResponse.class);

        assert updateCookieRequest.getId() == resultResponse.getId();
        Assert.assertEquals(updateCookieResponse.getName(), resultResponse.getName());
        Assert.assertEquals(updateCookieResponse.getDescription(), resultResponse.getDescription());
        Assert.assertArrayEquals(updateCookieResponse.getFileData(), resultResponse.getFileData());
        Assert.assertEquals(updateCookieResponse.getCookieAddingStatus(),
                                    resultResponse.getCookieAddingStatus());
        Assert.assertEquals(updateCookieResponse.getRating(), resultResponse.getRating());
        Assert.assertEquals(updateCookieRequest.getCookieOwner(), resultResponse.getCookieOwner());
    }

}
