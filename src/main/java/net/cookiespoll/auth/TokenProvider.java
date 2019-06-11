package net.cookiespoll.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Service
public class TokenProvider {
    public static final String GOOGLE_API = "https://www.googleapis.com/oauth2/v3/tokeninfo?id_token=";

    private RestTemplate restTemplate;

    @Autowired
    public TokenProvider(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

//    public TokenProvider() {
//        restTemplate = new RestTemplate();
//    }

    public String createToken(Authentication authentication) {
        DefaultOidcUser defaultOidcUser = (DefaultOidcUser) authentication.getPrincipal();

        return defaultOidcUser.getIdToken().getTokenValue();
    }

    public Optional <Map<String, String>> getUserFromToken(String token) throws IOException {
//        RestTemplate restTemplate = new RestTemplate();
//        try {
//            ResponseEntity<String> response = restTemplate.getForEntity(GOOGLE_API + token.trim(), String.class);
                Map <String, String> response = restTemplate.getForObject(GOOGLE_API + token.trim(), Map.class);
                return Optional.ofNullable(response);
//            ObjectMapper objectMapper = new ObjectMapper();
//            return Optional.of(objectMapper.readValue(response.getBody(), objectMapper.getTypeFactory().constructMapType(Map.class, String.class, String.class)));

//        } catch (IOException e) {
//            LOGGER.error("TokenProvider method getUserFromToken raised exception {} ", e.getMessage());
//        }
//        return Optional.empty();
    }

//        try (BufferedReader in = new BufferedReader(new InputStreamReader(((HttpURLConnection) (new URL(GOOGLE_API + token.trim()))
//                .openConnection()).getInputStream(), Charset.forName(ENCODING)))) {
//
//            StringBuffer b = new StringBuffer();
//            String inputLine;
//            while ((inputLine = in.readLine()) != null) {
//                b.append(inputLine + "\n");
//            }
//
//            ObjectMapper objectMapper = new ObjectMapper();
//
//            return Optional.of(objectMapper.readValue(b.toString(), objectMapper.getTypeFactory().constructMapType(Map.class, String.class, String.class)));
//        } catch (Exception e) {
//            e.getMessage();
//        }
//        return Optional.empty();
//    }

}
