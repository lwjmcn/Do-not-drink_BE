package com.jorupmotte.donotdrink.common.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.jorupmotte.donotdrink.common.dto.response.ResponseDto;

@RestControllerAdvice
public class ValidationExceptionHandler {

  @ExceptionHandler({MethodArgumentNotValidException.class, HttpMessageNotReadableException.class})
  public ResponseEntity<ResponseDto> validationExceptionHandler(Exception exception){
    return  ResponseDto.validationFail();
  }
  
}
