package net.cookiespoll.validation;

import net.cookiespoll.exception.EmailDomenException;
import org.springframework.stereotype.Component;

@Component
public class EmailDomenValidator {

    public final static String LINEATE = "lineate.com";
    public final static String THUMBTACK = "thumbtack.com";

    public void validate(String emailDomen) throws EmailDomenException {
        if (emailDomen == null) {
            throw new EmailDomenException("Email domen is null");
        }
        if ((!emailDomen.equals(LINEATE)) && (!emailDomen.equals(THUMBTACK))) {
            throw new EmailDomenException("Email with this domen can be logged in");
        }
    }

}
