package net.cookiespoll.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import net.cookiespoll.dto.AddCookieRequest;
import net.cookiespoll.dto.AddCookieResponse;
import net.cookiespoll.dto.UpdateCookieRequest;
import net.cookiespoll.dto.UpdateCookieResponse;
import net.cookiespoll.exception.ControllerExceptionHandler;
import net.cookiespoll.model.Cookie;
import net.cookiespoll.model.CookieAddingStatus;
import net.cookiespoll.service.CookieService;
import net.cookiespoll.validation.FileValidator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc

public class TestCookieController {

    private MockMvc mockMvc;
    private CookieService cookieService = mock(CookieService.class);
    private FileValidator fileValidator = new FileValidator();
    private CookiesController cookiesController = new CookiesController(cookieService, fileValidator);
    private byte [] byteArray = "Photo".getBytes();
    private Cookie cookie = new Cookie("cookie", "tasty cookie", byteArray, CookieAddingStatus.WAITING,
            0, 1);
    private MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "testcookie",
            "image/jpg", byteArray);
    private MockMultipartFile addCookieDtoRequest = new MockMultipartFile("data", "",
            "application/json", ("{\"name\":\"cookie\", \"description\": \"tasty cookie\"}").getBytes());

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(cookiesController).setControllerAdvice
                  (new ControllerExceptionHandler()).build();
    }

    @Test
    public void testAddCookieValidRequest() throws Exception {
        int userId = 1;
        when(cookieService.addCookie((any(AddCookieRequest.class)), any(MockMultipartFile.class), userId))
                .thenReturn(cookie);

        String response = mockMvc.perform(MockMvcRequestBuilders.multipart("/cookies")
                .file(mockMultipartFile)
                .file(addCookieDtoRequest)
        ).andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        verify(cookieService).addCookie((any(AddCookieRequest.class)), any(MockMultipartFile.class), userId);

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

/*
    @Test
    public void getCookiesByParam() throws Exception {
        List<Cookie> cookies = new ArrayList<>();
        Cookie cookieWith1Id = new Cookie(1, "cookie", "tasty cookie", new byte[2],
                CookieAddingStatus.WAITING, 0);
        Cookie cookieWith2Id = new Cookie(2,"cookie", "tasty cookie", new byte[2],
                CookieAddingStatus.WAITING, 0);
        CookieAddingStatus cookieAddingStatus = CookieAddingStatus.WAITING;
        Integer userId = 1;
        cookies.add(cookieWith1Id);
        cookies.add(cookieWith2Id);
        when(cookieService.getCookiesByParam(cookieAddingStatus)).thenReturn(cookies);

        mockMvc.perform(MockMvcRequestBuilders.get("/cookies/lists")
                .param("id", String.valueOf(userId))
        ).andExpect(status().isOk())
               .andExpect(content().string("[{\"id\":1,\"name\":\"cookie\",\"description\":" +
                        "\"tasty cookie\",\"fileData\":\"AAA=\",\"cookieAddingStatus\":\"WAITING\",\"rating\":0}," +
                        "{\"id\":2,\"name\":\"cookie\",\"description\":\"tasty cookie\",\"fileData\":\"AAA=\"," +
                        "\"cookieAddingStatus\":\"WAITING\",\"rating\":0}]"));

    }
*/

    @Test
    public void testUpdateCookie () throws Exception {
        UpdateCookieRequest updateCookieRequest = new UpdateCookieRequest(
                1, "cookie", "tasty cookie", byteArray,
                CookieAddingStatus.APPROVED, 0, 1);
        Gson gson = new Gson();
        String request = gson.toJson(updateCookieRequest);
        UpdateCookieResponse updateCookieResponse = new UpdateCookieResponse(
                1, "cookie", "tasty cookie", byteArray,
                CookieAddingStatus.APPROVED, 0, 1);

        when(cookieService.updateCookie(any(UpdateCookieRequest.class))).thenReturn(cookie);

        String response = mockMvc.perform(MockMvcRequestBuilders.patch("/cookies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
        ).andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        verify(cookieService).updateCookie(any(UpdateCookieRequest.class));

        UpdateCookieResponse resultResponse = new ObjectMapper().readValue(response,
                                                            UpdateCookieResponse.class);

        assert updateCookieRequest.getId() == resultResponse.getId();
        Assert.assertEquals(updateCookieResponse.getName(), resultResponse.getName());
        Assert.assertEquals(updateCookieResponse.getDescription(), resultResponse.getDescription());
        Assert.assertArrayEquals(updateCookieResponse.getFileData(), resultResponse.getFileData());
        Assert.assertEquals(updateCookieResponse.getCookieAddingStatus(), resultResponse.
                                                                                        getCookieAddingStatus());
        Assert.assertEquals(updateCookieResponse.getRating(), resultResponse.getRating());

    }

}

