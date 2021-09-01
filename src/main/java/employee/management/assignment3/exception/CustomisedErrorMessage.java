package employee.management.assignment3.exception;

import lombok.Getter;

import java.util.Date;
@Getter
public class CustomisedErrorMessage {
    private String message;
    private String details;
    private Date date;

    public CustomisedErrorMessage(String message, String details, Date date) {
        this.message = message;
        this.details = details;
        this.date = date;
    }

}
