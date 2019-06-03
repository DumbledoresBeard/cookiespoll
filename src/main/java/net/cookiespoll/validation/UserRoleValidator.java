package net.cookiespoll.validation;

import net.cookiespoll.exception.UserRoleValidationException;
import net.cookiespoll.model.user.Role;
import net.cookiespoll.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserRoleValidator {

    private UserService userService;

    @Autowired
    public UserRoleValidator(UserService userService) {
        this.userService = userService;
    }

    public void validateUserRole (String userId) throws UserRoleValidationException {
        if (!userService.getRole(userId).equals(Role.ADMIN)) {
            throw new UserRoleValidationException("Operation is permitted only for admin user");
        }
    }
}
