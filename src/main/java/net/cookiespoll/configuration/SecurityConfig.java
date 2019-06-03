package net.cookiespoll.configuration;

import net.cookiespoll.auth.HttpCookieOAuth2AuthorizationRequestRepository;
import net.cookiespoll.auth.OAuth2AuthenticationSuccessHandler;
import net.cookiespoll.auth.TokenAuthenticationFilter;
import net.cookiespoll.auth.TokenProvider;
import net.cookiespoll.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private UserService userService;
    private OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private TokenProvider tokenProvider;
    private HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

    @Autowired
    public SecurityConfig(UserService userService, OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler,
                          TokenProvider tokenProvider,
                          HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository) {
        this.userService = userService;
        this.oAuth2AuthenticationSuccessHandler = oAuth2AuthenticationSuccessHandler;
        this.tokenProvider = tokenProvider;
        this.httpCookieOAuth2AuthorizationRequestRepository = httpCookieOAuth2AuthorizationRequestRepository;
    }

    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter(tokenProvider, userService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf()
                .disable()
                .formLogin()
                .disable()
                .httpBasic()
                .disable()
                .exceptionHandling()
                .and().authorizeRequests()
                .antMatchers( "/auth/**", "/oauth2/**", "https://accounts.google.com/o/oauth2/")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2Login()
                .authorizationEndpoint()
                .baseUri("/oauth2/authorize")
                .authorizationRequestRepository(httpCookieOAuth2AuthorizationRequestRepository)
                .and()
                .redirectionEndpoint()
                .baseUri("/oauth2/callback/*")
                .and()
                .userInfoEndpoint()
                .oidcUserService(userService)
                .and()
               .successHandler(oAuth2AuthenticationSuccessHandler);

        http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}