package pro.sky.coursework2.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
public class MethodNotAllowed extends RuntimeException{
    public MethodNotAllowed() {
    }

    public MethodNotAllowed(String message) {
        super(message);
    }
}
