package net.cookiespoll.service;

import net.cookiespoll.dao.UserDao;
import net.cookiespoll.exception.EmailDomenException;
import net.cookiespoll.model.user.Admin;
import net.cookiespoll.model.user.Role;
import net.cookiespoll.model.user.User;
import net.cookiespoll.validation.EmailDomenValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserService extends OidcUserService {
    private UserDao userDao;
    private EmailDomenValidator emailDomenValidator;

    @Autowired
    public UserService(UserDao userDao, EmailDomenValidator emailDomenValidator) {
        this.userDao = userDao;
        this.emailDomenValidator = emailDomenValidator;
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

    private OidcUser processOidcUser(OidcUserRequest userRequest, OidcUser oidcUser) throws EmailDomenException {
        Map<String, Object> attr = oidcUser.getAttributes();

        String[] email = ((String)attr.get("email")).split("@");
        emailDomenValidator.validate(email[1]);

        User user = userDao.getById((String) attr.get("sub"));
        if (user == null) {
            user = new User();
            user.setId((String) attr.get("sub"));
            user.setName((String) attr.get("name"));
            user.setLogin((String) attr.get("email"));

            List<String> adminsLogins = userDao.getAdmins()
                    .stream()
                    .map(Admin::getLogin)
                    .collect(Collectors.toList());
            if (adminsLogins.contains((String)attr.get("email"))) {
                user.setRole(Role.ADMIN);
            } else {
                user.setRole(Role.USER);
            }
            userDao.insert(user);
        }

        return oidcUser;
    }

    public Role getRole (String id) {
        User user = userDao.getById(id);
        return user.getRole();
    }

    public User getById (String id) {
        return userDao.getById(id);
    }

    public User update(User user) {
        return userDao.update(user);
    }
}
