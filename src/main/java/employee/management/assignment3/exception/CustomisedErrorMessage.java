package employee.management.assignment3.exception;

import java.util.Date;

public class CustomisedErrorMessage {
    private String message;
    private String details;
    private Date date;

    public CustomisedErrorMessage(String message, String details, Date date) {
        this.message = message;
        this.details = details;
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }

    public Date getDate() {
        return date;
    }
}
