package net.cookiespoll.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Optional;

@Service
public class TokenProvider {

    public static final String GOOGLE_API = "https://www.googleapis.com/oauth2/v3/tokeninfo?id_token=";
    public static final String ENCODING = "UTF-8";

    public String createToken(Authentication authentication) {
        DefaultOidcUser defaultOidcUser = (DefaultOidcUser) authentication.getPrincipal();

        return defaultOidcUser.getIdToken().getTokenValue();
    }

    public Optional <Map<String, String>> getUserFromToken(String token) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(((HttpURLConnection) (new URL(GOOGLE_API + token.trim()))
                .openConnection()).getInputStream(), Charset.forName(ENCODING)))) {

            StringBuffer b = new StringBuffer();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                b.append(inputLine + "\n");
            }

            ObjectMapper objectMapper = new ObjectMapper();

            return Optional.of(objectMapper.readValue(b.toString(), objectMapper.getTypeFactory().constructMapType(Map.class, String.class, String.class)));
        } catch (Exception e) {
            e.getMessage();
        }
        return Optional.empty();
    }
}
