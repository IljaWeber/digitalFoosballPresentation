package com.valtech.digitalFoosball.domain.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {NameDuplicateException.class})
    public ResponseEntity<Object> handleDuplicateException(NameDuplicateException exception, WebRequest request) {
        String errorMessageDescription = exception.getLocalizedMessage();

        if (errorMessageDescription == null){
            errorMessageDescription = exception.toString();
        } else {
            errorMessageDescription += " is used more than once";
        }

        ErrorMessage errorMessage = new ErrorMessage(new Date(), errorMessageDescription);

        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
