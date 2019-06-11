package net.cookiespoll.configuration;

import net.cookiespoll.auth.TokenAuthenticationFilter;
import net.cookiespoll.auth.TokenProvider;
import net.cookiespoll.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AuthConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public TokenProvider tokenProvider(RestTemplate restTemplate) {
        return new TokenProvider(restTemplate);
    }

    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter(TokenProvider tokenProvider, UserService userService) {
        return new TokenAuthenticationFilter(tokenProvider, userService);
    }
}
