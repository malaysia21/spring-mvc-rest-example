package guru.springfamework.services;

import org.springframework.stereotype.Component;

public class ResourceNotFoundException extends RuntimeException {

    private long id;

    public ResourceNotFoundException() {
    }
    public ResourceNotFoundException(long id) {
        this.id=id;
    }
    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceNotFoundException(Throwable cause) {
        super(cause);
    }

    public ResourceNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public long getId() {
        return id;
    }

}
