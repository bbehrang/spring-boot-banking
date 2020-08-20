package com.app.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Data
public class ApiError {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private HttpStatus status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> errors;
    private String debugMessage;


    private ApiError(){
        timestamp = LocalDateTime.now();
    }
    public ApiError(HttpStatus status, String debugMessage, List<String> errors) {
        this();
        this.status = status;
        this.debugMessage = debugMessage;
        this.errors = errors;
    }
    public ApiError(HttpStatus status, String debugMessage, String error) {
        this();
        this.status = status;
        this.debugMessage = debugMessage;
        errors = Arrays.asList(error);
    }
    public ApiError(HttpStatus status, String debugMessage) {
        this();
        this.status = status;
        this.debugMessage = debugMessage;
    }


}
