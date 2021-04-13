package com.swfiona.tlctrip.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.swfiona.tlctrip.model.ErrorResponse;

@RestController
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
 
    @ExceptionHandler(BoroughNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public final ErrorResponse handleBoroughNotFoundException(BoroughNotFoundException ex) {
    	ErrorResponse error = new ErrorResponse();
        error.setMessage(ex.getMessage());
        error.setTime(new Date().toString());
        error.setStatus(HttpStatus.NOT_FOUND.toString());
        
        return error;
    }
    
    @ExceptionHandler(VehicleNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public final ErrorResponse handleVehicleNotFoundException(VehicleNotFoundException ex) {
    	ErrorResponse error = new ErrorResponse();
        error.setMessage(ex.getMessage());
        error.setTime(new Date().toString());
        error.setStatus(HttpStatus.NOT_FOUND.toString());
        
        return error;
    }
    
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final ErrorResponse handleAllExceptions(Exception ex) {
        ErrorResponse error = new ErrorResponse();
        error.setMessage(ex.getLocalizedMessage());
        error.setTime(new Date().toString());
        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());

        return error;
    }

}
