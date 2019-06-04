package net.cookiespoll.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Optional;

@Service
public class TokenProvider {

    public String createToken(Authentication authentication) {
        DefaultOidcUser defaultOidcUser = (DefaultOidcUser) authentication.getPrincipal();
        return defaultOidcUser.getIdToken().getTokenValue();
    }

    public Optional<Map<String, String>> getUserFromToken(String token) {
            /*BufferedReader in = null;*/
            try (BufferedReader in = new BufferedReader(new InputStreamReader(
                    ((HttpURLConnection) (new URL("https://www.googleapis.com/oauth2/v3/tokeninfo?id_token=" + token.trim()))
                            .openConnection()).getInputStream(), Charset.forName("UTF-8")))) {
              /*  in = new BufferedReader(new InputStreamReader(
                        ((HttpURLConnection) (new URL("https://www.googleapis.com/oauth2/v3/tokeninfo?id_token=" + token.trim()))
                                .openConnection()).getInputStream(), Charset.forName("UTF-8")));*/

                StringBuffer b = new StringBuffer();
                String inputLine;
                while ((inputLine = in.readLine()) != null){
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
