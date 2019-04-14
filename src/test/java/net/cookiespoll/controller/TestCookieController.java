package net.cookiespoll.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.cookiespoll.daoimpl.CookieDaoImpl;
import net.cookiespoll.dto.AddCookieDtoRequest;
import net.cookiespoll.dto.AddCookieDtoResponse;
import net.cookiespoll.model.Cookie;
import net.cookiespoll.model.CookieAddingStatus;
import net.cookiespoll.service.CookieService;
import net.cookiespoll.validation.FileValidator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@WebAppConfiguration
public class TestCookieController {

    private MockMvc mockMvc;
    private CookieService cookieService = mock(CookieService.class);
    private FileValidator fileValidator = new FileValidator();
    private CookiesController cookiesController = new CookiesController(cookieService, fileValidator);
    private byte [] byteArray = "Photo".getBytes();
    private Cookie cookie = new Cookie("cookie", "tasty cookie", byteArray, CookieAddingStatus.WAITING);
    private MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "testcookie", "image/jpg", byteArray);
    private MockMultipartFile addCookieDtoRequest = new MockMultipartFile("data", "", "application/json", "{\"name\":\"cookie\", \"description\": \"tasty cookie\"}".getBytes());

    @Before
    public void initController() {
        mockMvc = MockMvcBuilders.standaloneSetup(cookiesController).build();
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
}

