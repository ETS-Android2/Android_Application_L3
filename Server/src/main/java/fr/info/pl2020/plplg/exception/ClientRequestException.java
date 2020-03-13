package fr.info.pl2020.plplg.exception;

import org.springframework.http.HttpStatus;

public class ClientRequestException extends Exception {

    private final String clientMessage;
    private final HttpStatus status;

    public ClientRequestException(String clientMessage, HttpStatus status) {
        super();
        this.clientMessage = "{\"error\":\"" + clientMessage + "\"}";
        this.status = status;
    }

    public ClientRequestException(String errorMessage, String clientMessage, HttpStatus status) {
        super(errorMessage);
        this.clientMessage = "{\"error\":\"" + clientMessage + "\"}";
        this.status = status;
    }

    public ClientRequestException(Throwable cause, String clientMessage, HttpStatus status) {
        super(cause);
        this.clientMessage = clientMessage;
        this.status = status;
    }

    public String getClientMessage() {
        return clientMessage;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
