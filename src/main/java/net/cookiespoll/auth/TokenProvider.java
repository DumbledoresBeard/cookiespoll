package net.cookiespoll.auth;


import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Service;

@Service
public class TokenProvider {

   /* public static final String CLIENT_ID = '154501297407-1dhfqh3s2nm0mj2sg4m0n00r2kv9qlnl.apps.googleusercontent.com';*/

    public String createToken(Authentication authentication) {
        DefaultOidcUser defaultOidcUser = (DefaultOidcUser) authentication.getPrincipal();
        return defaultOidcUser.getIdToken().getTokenValue();
    }

    public String getUserIdFromToken() {
        return "106744814838713798626";
    }

    /*public boolean validateToken(String authToken) {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier(new UrlFetchTransport(), new JacksonFactory(), CLIENT_ID);

// (Receive idTokenString by HTTPS POST)

        GoogleIdToken idToken = verifier.verify(authToken);
    }*/
}
