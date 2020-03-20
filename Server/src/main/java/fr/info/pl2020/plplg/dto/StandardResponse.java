package fr.info.pl2020.plplg.dto;

public class StandardResponse {
    private String message;

    public StandardResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
