package com.digital.realty.jwt.exception;

import com.digital.realty.jwt.model.ResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@RestControllerAdvice
public class ExceptionAdvice extends ResponseEntityExceptionHandler{

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ResponseModel> handleGenericNotFoundException(Exception e) {

        String message = e.getMessage();
        ResponseModel responseModel = new ResponseModel(message);
        return new ResponseEntity<>(responseModel, HttpStatus.NOT_FOUND);
    }
}