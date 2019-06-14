package net.cookiespoll.utils;

import net.cookiespoll.model.user.User;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserUtils {

    public static User getUserFromSession() {
        return  (User) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
    }
}
