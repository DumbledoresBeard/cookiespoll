package net.cookiespoll.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.cookiespoll.dto.AddCookieDtoRequest;
import net.cookiespoll.dto.AddCookieDtoResponse;
import net.cookiespoll.exception.ControllerExceptionHandler;
import net.cookiespoll.model.Cookie;
import net.cookiespoll.model.CookieAddingStatus;
import net.cookiespoll.service.CookieService;
import net.cookiespoll.validation.FileValidator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc

public class TestCookieController {

    private MockMvc mockMvc;
    private CookieService cookieService = mock(CookieService.class);
    private FileValidator fileValidator = new FileValidator();
    private CookiesController cookiesController = new CookiesController(cookieService, fileValidator);
    private byte [] byteArray = "Photo".getBytes();
    private Cookie cookie = new Cookie("cookie", "tasty cookie", byteArray, CookieAddingStatus.WAITING);
    private MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "testcookie",
            "image/jpg", byteArray);
    private MockMultipartFile addCookieDtoRequest = new MockMultipartFile("data", "",
            "application/json", ("{\"name\":\"cookie\", \"description\": \"tasty cookie\"}").getBytes());
    private MockMultipartFile cookieNullName = new MockMultipartFile("data", "",
            "application/json", "{\"name\": null, \"description\": \"tasty cookie\"}".getBytes());
    private MockMultipartFile cookieTooShortName = new MockMultipartFile("data", "",
            "application/json", "{\"name\": \"c\", \"description\": \"tasty cookie\"}".getBytes());
    private MockMultipartFile cookieTooLongName = new MockMultipartFile("data", "",
            "application/json", ("{\"name\": \"tastycookietastycookietastycook\", \"description\": " +
            "\"tasty cookie\"}").getBytes());
    private MockMultipartFile cookieWithNullDescription = new MockMultipartFile("data", "",
            "application/json", "{\"name\": \"cookie\", \"description\": null}".getBytes());
    private MockMultipartFile cookieEmptyDescription = new MockMultipartFile("data", "",
            "application/json", "{\"name\": \"cookie\", \"description\": \"\"}".getBytes());
    private MockMultipartFile cookieWithTooLongDescription = new MockMultipartFile("data", "",
            "application/json", ("{\"name\": \"cookie\", \"description\": " +
            "\"tastycookietastycookietastycookietastycookietastycookietastycookietastycook" +
            "ietastycookietastycookietastycookietastycookietastycookietastycookietastycoo\"}").getBytes());
    private MockMultipartFile cookieNullFile = null;
    private MockMultipartFile cookieInvalidFileType = new MockMultipartFile("file", "testcookie",
            "text/plain", byteArray);
    private MockMultipartFile cookieEmptyFile = new MockMultipartFile("file", "testcookie",
            "text/plain", new byte[0]);
    private MockMultipartFile cookieExceededMaxFileSize = new MockMultipartFile("file", "testcookie",
            "image/jpg", new byte[1024 * 1024 * 7]);
    private List<Cookie> cookies = new ArrayList<>();
    private Cookie cookieWith1Id = new Cookie(1, "cookie", "tasty cookie", new byte[2],
            CookieAddingStatus.WAITING, 0);
    private Cookie cookieWith2Id = new Cookie(2,"cookie", "tasty cookie", new byte[2],
            CookieAddingStatus.WAITING, 0);
    private CookieAddingStatus cookieAddingStatus = CookieAddingStatus.WAITING;
    private int id = 1;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(cookiesController).setControllerAdvice
                  (new ControllerExceptionHandler()).build();
    }

    @Test
    public void testAddCookieValidRequest() throws Exception {
        when(cookieService.addCookie((any(AddCookieDtoRequest.class)), any(MockMultipartFile.class))).thenReturn(cookie);

        String response = mockMvc.perform(MockMvcRequestBuilders.multipart("/addcookie")
                .file(mockMultipartFile)
                .file(addCookieDtoRequest)
        ).andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        verify(cookieService).addCookie((any(AddCookieDtoRequest.class)), any(MockMultipartFile.class));

        AddCookieDtoResponse addCookieDtoResponse = new ObjectMapper().readValue(response, AddCookieDtoResponse.class);

        Assert.assertEquals(cookie.getName(), addCookieDtoResponse.getName());
        Assert.assertEquals(cookie.getDescription(), addCookieDtoResponse.getDescription());
        Assert.assertArrayEquals(cookie.getFileData(), addCookieDtoResponse.getFileData());
        Assert.assertEquals(cookie.getCookieAddingStatus(), addCookieDtoResponse.getCookieAddingStatus());

    }

    @Test
    public void testAddCookieWithNullName() throws Exception {

      mockMvc.perform(MockMvcRequestBuilders.multipart("/addcookie")
                .file(mockMultipartFile)
                .file(cookieNullName)
        ).andExpect(status().is(400))
              .andExpect(content().string("{\"errors\":[{\"fieldName\":\"name\"," +
                      "\"message\":\"Cookie name cannot be null\"}]}"));

    }

    @Test
    public void testAddCookieWithTooShortName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.multipart("/addcookie")
                .file(mockMultipartFile)
                .file(cookieTooShortName)
        ).andExpect(status().is(400))
                .andExpect(content().string("{\"errors\":[{\"fieldName\":\"name\"," +
                        "\"message\":\"Cookie name must be between 4 and 30 characters\"}]}"));
    }

    @Test
    public void testAddCookieWithTooLongName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.multipart("/addcookie")
                .file(mockMultipartFile)
                .file(cookieTooLongName)
        ).andExpect(status().is(400))
                .andExpect(content().string("{\"errors\":[{\"fieldName\":\"name\"," +
                        "\"message\":\"Cookie name must be between 4 and 30 characters\"}]}"));
    }


    @Test
    public void testAddCookieWithNullDescription() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.multipart("/addcookie")
                .file(mockMultipartFile)
                .file(cookieWithNullDescription)
        ).andExpect(status().is(400))
                .andExpect(content().string("{\"errors\":[{\"fieldName\":\"description\"," +
                        "\"message\":\"Cookie description cannot be null\"}]}"));

    }


    @Test
    public void testAddCookieEmptyDescription () throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.multipart("/addcookie")
                .file(mockMultipartFile)
                .file(cookieEmptyDescription)
        ).andExpect(status().is(400))
                .andExpect(content().string("{\"errors\":[{\"fieldName\":\"description\"," +
                        "\"message\":\"Cookie description must be less then 150 characters and cannot be empty\"}]}"));
    }

    @Test
    public void testAddCookieWithTooLongDescription () throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.multipart("/addcookie")
                .file(mockMultipartFile)
                .file(cookieWithTooLongDescription)
        ).andExpect(status().is(400))
                .andExpect(content().string("{\"errors\":[{\"fieldName\":\"description\"," +
                        "\"message\":\"Cookie description must be less then 150 characters and cannot be empty\"}]}"));
    }


    @Test
    public void testAddCookieNullFile() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.multipart("/addcookie")
                .file(cookieNullFile)
                .file(addCookieDtoRequest)
        ).andExpect(status().is(400))
                .andExpect(content().string("{\"errors\":[{\"fieldName\":\"file\"," +
                        "\"message\":\"File is null, please, upload a file\"}]}"));
    }

    @Test
    public void testAddCookieEmptyFile() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.multipart("/addcookie")
                .file(cookieEmptyFile)
                .file(addCookieDtoRequest)
        ).andExpect(status().is(400))
                .andExpect(content().string("{\"errors\":[{\"fieldName\":\"file\"," +
                        "\"message\":\"File is empty, please, upload jpg, jpeg or png file\"}]}"));
    }

    @Test
    public void testAddCookieInvalidFileType () throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.multipart("/addcookie")
                .file(cookieInvalidFileType)
                .file(addCookieDtoRequest)
        ).andExpect(status().is(400))
                .andExpect(content().string("{\"errors\":[{\"fieldName\":\"file\"," +
                        "\"message\":\"File type is not supported, valid file types: jpg, jpeg or png\"}]}"));
    }

    @Test
    public void testAddCookieExceededMaxFileSize () throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.multipart("/addcookie")
                .file(cookieExceededMaxFileSize)
                .file(addCookieDtoRequest)
        ).andExpect(status().is(500));
    /*            .andExpect((ResultMatcher) jsonPath("$.message", is("Maximum upload size exceeded; nested exception is java.lang.IllegalStateException: org.apache.tomcat.util.http.fileupload.FileUploadBase$SizeLimitExceededException: the request was rejected because its size (8055342) exceeds the configured maximum (5242880)")));*/

    }

    @Test
    public void getCookiesInWaitingStatus () throws Exception {
        cookies.add(cookieWith1Id);
        cookies.add(cookieWith2Id);
        when(cookieService.getCookieListByAddingStatus(cookieAddingStatus)).thenReturn(cookies);

        String response = mockMvc.perform(MockMvcRequestBuilders.get("/addcookie")
                .param("id", String.valueOf(id))
        ).andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        verify(cookieService).getCookieListByAddingStatus(cookieAddingStatus);

        List<Cookie> resultCookieList = new ObjectMapper().readValue(response, ArrayList.class);

        Assert.assertEquals(resultCookieList.get(0).getId(), cookieWith1Id.getId());
        Assert.assertEquals(resultCookieList.get(0).getName(), cookieWith1Id.getName());
        Assert.assertEquals(resultCookieList.get(0).getDescription(), cookieWith1Id.getDescription());
        Assert.assertArrayEquals(resultCookieList.get(0).getFileData(), cookieWith1Id.getFileData());
        Assert.assertEquals(resultCookieList.get(0).getCookieAddingStatus(), cookieWith1Id.getCookieAddingStatus());
        Assert.assertEquals(resultCookieList.get(0).getRating(), cookieWith1Id.getRating());

        Assert.assertEquals(resultCookieList.get(1).getId(), cookieWith2Id.getId());
        Assert.assertEquals(resultCookieList.get(1).getName(), cookieWith2Id.getName());
        Assert.assertEquals(resultCookieList.get(1).getDescription(), cookieWith2Id.getDescription());
        Assert.assertArrayEquals(resultCookieList.get(1).getFileData(), cookieWith2Id.getFileData());
        Assert.assertEquals(resultCookieList.get(1).getCookieAddingStatus(), cookieWith2Id.getCookieAddingStatus());
        Assert.assertEquals(resultCookieList.get(1).getRating(), cookieWith2Id.getRating());
    }

}

