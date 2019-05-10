package net.cookiespoll.dao;

import net.cookiespoll.model.user.User;
import org.springframework.stereotype.Component;

@Component
public interface UserDao {

    User insert (User user);

    User getById(String id);

    User update (User user);

}
