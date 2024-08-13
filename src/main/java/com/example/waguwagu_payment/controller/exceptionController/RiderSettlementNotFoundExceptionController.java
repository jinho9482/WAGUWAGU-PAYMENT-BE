package com.example.waguwagu_payment.controller.exceptionController;

import com.example.waguwagu_payment.global.exception.RiderSettlementNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RiderSettlementNotFoundExceptionController {
    @ExceptionHandler(RiderSettlementNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String RiderSettlementNotFoundExceptionHandler(RiderSettlementNotFoundException e) {
        return e.getMessage();
    }
}
