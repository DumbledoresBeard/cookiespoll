package net.cookiespoll.controller;
import net.cookiespoll.daoimpl.CookieDaoImpl;
import net.cookiespoll.dto.AddCookieDtoRequest;
import net.cookiespoll.model.Cookie;
import net.cookiespoll.service.CookieService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
/*
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
    @Mock
    private CookieService cookieService;
    @InjectMocks
    private CookiesController cookiesController;

    private Cookie cookie = new Cookie("cookie", "tasty cookie", new byte[2]);
    private MockMultipartFile mockMultipartFile = new MockMultipartFile("testcookie.jpg", "testcookie", "image/jpg", new byte[2]);
    private AddCookieDtoRequest addCookieDtoRequest = new AddCookieDtoRequest("cookie", "tasty cookie", mockMultipartFile);

    @Before
    public void initController() {
        mockMvc = MockMvcBuilders.standaloneSetup(cookiesController).build();
    }

    @Test
    public void addCookieValidRequest() throws Exception {
        when(cookieService.addCookie(addCookieDtoRequest)).thenReturn(cookie);

        mockMvc.perform(MockMvcRequestBuilders.multipart("/addcookie")
                .sessionAttr("addCookieDtoRequest", addCookieDtoRequest)
        ).andExpect(status().isOk());



    }
}
*/
