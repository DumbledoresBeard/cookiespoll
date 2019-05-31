package net.cookiespoll.configuration;

import net.cookiespoll.model.user.OAuth2AuthenticationSuccessHandler;
import net.cookiespoll.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private UserService userService;
    private OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

    @Autowired
    public SecurityConfig(UserService userService, OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler) {
        this.userService = userService;
        this.oAuth2AuthenticationSuccessHandler = oAuth2AuthenticationSuccessHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .oauth2Login()
                .authorizationEndpoint()
                .baseUri("/oauth2/authorize")
                .and()
                .redirectionEndpoint()
                .baseUri("/oauth2/redirect/*")
                .and()
                .userInfoEndpoint()
                .oidcUserService(userService)
                .and()
                .successHandler(oAuth2AuthenticationSuccessHandler)
                .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll().logoutUrl("/cookiepoll/logout")
                .invalidateHttpSession(true)
                .clearAuthentication(true).logoutSuccessUrl("/cookiepoll/logout")
                .deleteCookies("JSESSIONID")
                .permitAll().and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
    }



}