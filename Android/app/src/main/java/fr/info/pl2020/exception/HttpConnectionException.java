package fr.info.pl2020.exception;

public class HttpConnectionException extends Exception {
    private Exception origin;
    private final String message = "Une erreur est survenue lors de la connexion avec le serveur. Veuillez réessayer ultérieurement";

    public HttpConnectionException() {
    }

    public HttpConnectionException(Exception origin) {
        this.origin = origin;
    }

    public Exception getOrigin() {
        return origin;
    }

    public String getMessage() {
        return message;
    }
}
