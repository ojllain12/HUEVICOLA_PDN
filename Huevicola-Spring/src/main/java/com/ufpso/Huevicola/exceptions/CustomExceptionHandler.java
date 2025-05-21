package com.ufpso.Huevicola.exceptions;

import com.ufpso.Huevicola.models.generic.ResponseMessage;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ControllerAdvice
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ResponseMessage> handleAllExceptions(Throwable ex) {
        ResponseMessage responseMessage = ResponseMessage.builder()
                .date(LocalDate.now())
                .message(Collections.singletonList(ex.getMessage()))
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build();
        return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AlreadyInUseException.class)
    public ResponseEntity<ResponseMessage> alreadyInUseExceptionHandler(AlreadyInUseException alreadyInUseException) {
        return new ResponseEntity<>(
                ResponseMessage.builder()
                        .date(LocalDate.now())
                        .message(List.of(alreadyInUseException.getMessage()))
                        .statusCode(HttpStatus.CONFLICT.value())
                        .build(),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseMessage> jakartaExceptionHandler(MethodArgumentNotValidException exception) {
        List<String> errorList = new ArrayList<>();
        exception.getBindingResult().getAllErrors().forEach(objectError ->
                errorList.add(
                        ((FieldError) objectError).getField() + "|" + objectError.getDefaultMessage()
                )
        );
        return new ResponseEntity<>(
                ResponseMessage.builder()
                        .date(LocalDate.now())
                        .message(errorList)
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ResponseMessage> jakartaExceptionHandler2(ConstraintViolationException exception) {
        List<String> errorList = new ArrayList<>();
        exception.getConstraintViolations().forEach(objectError ->
                errorList.add(
                        objectError.getPropertyPath() + "|" + objectError.getMessageTemplate()
                )
        );
        return new ResponseEntity<>(
                ResponseMessage.builder()
                        .date(LocalDate.now())
                        .message(errorList)
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(AuthenticationFailedException.class)
    public ResponseEntity<ResponseMessage> authFailed(AuthenticationFailedException exception) {
        return new ResponseEntity<>(
                ResponseMessage.builder()
                        .date(LocalDate.now())
                        .message(Collections.singletonList(exception.getMessage()))
                        .statusCode(HttpStatus.FORBIDDEN.value())
                        .build(),
                HttpStatus.FORBIDDEN
        );
    }
}
