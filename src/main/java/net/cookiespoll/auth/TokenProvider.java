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

@Service
public class TokenProvider {

   /* public static final String CLIENT_ID = '154501297407-1dhfqh3s2nm0mj2sg4m0n00r2kv9qlnl.apps.googleusercontent.com';*/

    public String createToken(Authentication authentication) {
        DefaultOidcUser defaultOidcUser = (DefaultOidcUser) authentication.getPrincipal();
        return defaultOidcUser.getIdToken().getTokenValue();
    }

    public Map<String,String> getUserIdFromToken(String token) {
            BufferedReader in = null;
            try {
                // get information from token by contacting the google_token_verify_tool url :
                in = new BufferedReader(new InputStreamReader(
                        ((HttpURLConnection) (new URL("https://www.googleapis.com/oauth2/v3/tokeninfo?id_token=" + token.trim()))
                                .openConnection()).getInputStream(), Charset.forName("UTF-8")));

                // read information into a string buffer :
                StringBuffer b = new StringBuffer();
                String inputLine;
                while ((inputLine = in.readLine()) != null){
                    b.append(inputLine + "\n");
                }

                // transforming json string into Map<String,String> :
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.readValue(b.toString(), objectMapper.getTypeFactory().constructMapType(Map.class, String.class, String.class));
    } catch (MalformedURLException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    } catch(Exception e){
        System.out.println("\n\n\tFailed to transform json to string\n");
        e.printStackTrace();
    } finally{
        if(in!=null){
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
        return null;
}

    /*public boolean validateToken(String authToken) {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier(new UrlFetchTransport(), new JacksonFactory(), CLIENT_ID);

// (Receive idTokenString by HTTPS POST)

        GoogleIdToken idToken = verifier.verify(authToken);
    }*/
}
