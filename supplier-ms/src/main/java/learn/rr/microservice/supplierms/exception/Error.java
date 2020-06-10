package learn.rr.microservice.supplierms.exception;

import java.util.Date;

public class Error {
    private Date date;
    private String message;
    private String errorDetails;

    public Error(Date date, String message, String errorDetails) {
        this.date = date;
        this.message = message;
        this.errorDetails = errorDetails;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorDetails() {
        return errorDetails;
    }

    public void setErrorDetails(String errorDetails) {
        this.errorDetails = errorDetails;
    }

    @Override
    public String toString() {
        return "Error{" +
                "date=" + date +
                ", message='" + message + '\'' +
                ", errorDetails='" + errorDetails + '\'' +
                '}';
    }
}
