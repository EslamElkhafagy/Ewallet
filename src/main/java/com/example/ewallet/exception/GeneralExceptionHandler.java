package com.example.ewallet.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
@ControllerAdvice
public class GeneralExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(BusinessExcption.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> processMethodNotSuppotredException(BusinessExcption e,WebRequest request){
        ErrorResponse errorResponse= new ErrorResponse();

        errorResponse.setMessage(e.getMessage());
        errorResponse.setHttpStatus(e.getHttpStatus());
        return new ResponseEntity<ErrorResponse>(errorResponse,e.getHttpStatus());
    }

}
