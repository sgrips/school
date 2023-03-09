package it.brt.helloworld.web;

import it.brt.helloworld.models.exception.InvalidArgException;
import it.brt.helloworld.models.exception.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class APIExceptionHandler {

    @ExceptionHandler(value= { Exception.class })
    protected ResponseEntity<Object> handleException(Exception ex, WebRequest request) throws Exception {

        if (ex instanceof InvalidArgException se) {
            return ResponseEntity.badRequest().build();
        }

        if (ex instanceof NotFoundException se) {
            return ResponseEntity.notFound().build();
        }

        if (ex instanceof MethodArgumentNotValidException me) {

            return ResponseEntity.badRequest().body(String.join(";", me.getBindingResult().getAllErrors().stream().map(e -> {
                return ((FieldError) e).getField() + ":" + e.getDefaultMessage();
            }).toList()));

        }

        throw ex;
    }
}
