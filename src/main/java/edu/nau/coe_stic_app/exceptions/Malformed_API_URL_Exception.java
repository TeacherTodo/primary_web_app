package edu.nau.coe_stic_app.exceptions;

public class Malformed_API_URL_Exception extends RuntimeException {
    public Malformed_API_URL_Exception(String message, Throwable error) {
        super(message, error);
    }
}
