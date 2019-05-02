package net.cookiespoll.service;

import net.cookiespoll.dao.UserDao;
import net.cookiespoll.user.User;
import net.cookiespoll.user.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class UserService extends OidcUserService {
    private UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(userRequest);

        try {
            return processOidcUser(userRequest, oidcUser);
        } catch (Exception ex) {
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OidcUser processOidcUser(OidcUserRequest userRequest, OidcUser oidcUser) {
        Map<String, Object> attr = oidcUser.getAttributes();

        User user = userDao.getById((String) attr.get("sub"));
        if (user == null) {
            user = new User();
            user.setId((String) attr.get("sub"));
            user.setName((String) attr.get("name"));
            user.setLogin((String) attr.get("email"));
            user.setRole(Role.USER);

           userDao.insert(user);
        }

        return oidcUser;
    }

    public Role getRole (String id) {
        User user = userDao.getById(id);
        return user.getRole();
    }
}
