package com.tornikeshelia.gsgyoutubeapi.exception;

import com.tornikeshelia.gsgyoutubeapi.model.enums.GsgError;
import com.tornikeshelia.gsgyoutubeapi.model.exception.GeneralException;
import com.tornikeshelia.gsgyoutubeapi.model.exception.GeneralExceptionResponse;
import com.tornikeshelia.gsgyoutubeapi.model.exception.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerAdviceException {

    @ExceptionHandler
    public ResponseEntity<?> handleException(GeneralException e) {
        return new ResponseEntity<>(new GeneralExceptionResponse(e.getGsgError().getDescription()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    GeneralExceptionResponse handleConstraintViolationException(ConstraintViolationException e) {
        List<ValidationException> violations = e.getConstraintViolations()
                .stream()
                .map(ValidationException::transformConstraintViolation)
                .collect(Collectors.toList());
        return new GeneralExceptionResponse(GsgError.INVALID_REQUEST.getDescription(), violations);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    GeneralExceptionResponse handleConstraintViolationException(MethodArgumentNotValidException e) {
        List<ValidationException> violations = e.getBindingResult().getFieldErrors()
                .stream()
                .map(ValidationException::transformFieldError)
                .collect(Collectors.toList());
        return new GeneralExceptionResponse(GsgError.INVALID_REQUEST.getDescription(), violations);
    }

}
