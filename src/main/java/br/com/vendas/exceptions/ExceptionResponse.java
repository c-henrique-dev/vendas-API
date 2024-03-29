package br.com.vendas.exceptions;

import java.util.Date;

public class ExceptionResponse {

    private Date timestamp;
    private String message;
    private String statusCode;

    public ExceptionResponse(Date timestamp, String message, String statusCode) {
        super();
        this.timestamp = timestamp;
        this.message = message;
        this.statusCode = statusCode;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getStatusCode() {
        return statusCode;
    }

}