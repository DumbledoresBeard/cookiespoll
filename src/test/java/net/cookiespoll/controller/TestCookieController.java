package net.cookiespoll.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import net.cookiespoll.daoimpl.CookieDaoImpl;
import net.cookiespoll.dto.AddCookieDtoRequest;
import net.cookiespoll.dto.AddCookieDtoResponse;
import net.cookiespoll.exception.ControllerExceptionHandler;
import net.cookiespoll.exception.ErrorResponse;
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
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.ExceptionHandlerMethodResolver;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    private MockMultipartFile addCookieDtoRequest = new MockMultipartFile("data", "", "application/json",
            ("{\"name\":\"cookie\", \"description\": \"tasty cookie\"}").getBytes());
    private MockMultipartFile addCookieNullName = new MockMultipartFile("data", "", "application/json",
            "{\"name\": null, \"description\": \"tasty cookie\"}".getBytes());


    @Before
    public void init() {
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

    @Test
    public void testAddCookieWithNullName() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.multipart("/addcookie")
                .file(mockMultipartFile)
                .file(addCookieNullName)
        ).andExpect(status().is(400))
                .andExpect(content().string("{\"errors\":[{\"fieldName\":\"name\",\"message\":\"Cookie name cannot be null\"}]}"));


       /* ErrorResponse errorResponse = new ObjectMapper().readValue(response, ErrorResponse.class);

        List<ErrorResponse.ErrorDetails> errorDetails = new ArrayList<>();
        ErrorResponse.ErrorDetails error = new ErrorResponse.ErrorDetails();
        error.setFieldName("name");
        error.setMessage("Cookie name cannot be null");
        errorDetails.add(error);
        ErrorResponse expectedErrorResponse = new ErrorResponse();
        errorResponse.setErrors(errorDetails);

        Assert.assertEquals(expectedErrorResponse.getErrors().get(0).getFieldName(), errorResponse.getErrors().get(0).getFieldName());
        Assert.assertEquals(expectedErrorResponse.getErrors().get(0).getMessage(), errorResponse.getErrors().get(0).getMessage());*/
    }
}

