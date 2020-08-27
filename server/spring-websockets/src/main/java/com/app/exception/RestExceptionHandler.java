package com.app.exception;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.google.protobuf.Api;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException  ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request){
        List<String> errors = new ArrayList<>();
        for(FieldError error: ex.getBindingResult().getFieldErrors())
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        for(ObjectError error: ex.getBindingResult().getGlobalErrors())
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
        return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex){
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage(), ex.getMessage());
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
    @ExceptionHandler(InsufficientFundsException.class)
    protected ResponseEntity<Object> InsufficientFunds(InsufficientFundsException ex){
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), ex.getMessage());
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex){
        ConstraintViolationException cex = (ConstraintViolationException) ex.getCause();
        Pattern pattern = Pattern.compile("PUBLIC.(\\w+)[(](\\w+)[)]");
        Matcher m = pattern.matcher(ex.getMostSpecificCause().getMessage());
        ApiError apiError;
        if(m.find()){
            String columnName = m.group();
            apiError = new ApiError(HttpStatus.CONFLICT, ex.getMessage(),
                    columnName.substring(7).toLowerCase() + " Should be unique");
        }
        else{
            apiError = new ApiError(HttpStatus.CONFLICT, ex.getMessage());
        }
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        JsonMappingException jme = (JsonMappingException) ex.getCause();
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), jme.getOriginalMessage());
        return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);
    }
    @ExceptionHandler(EmptyResultDataAccessException.class)
    protected ResponseEntity<Object> handleEmptyResultDataAccess(EmptyResultDataAccessException ex){

        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage());
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
