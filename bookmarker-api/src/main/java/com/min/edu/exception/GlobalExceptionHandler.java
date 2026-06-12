package com.min.edu.exception;

// 일반적으로 RestController이든, Controller이든 모든 요청에 대한 오류 사항은 HTML로 반환함
// HTML로 반환하면 UI/UX에서 모두 200으로 처리되므로, 반환되는 JSON의 값을 통해서 new Error()처리를 해줘야 함
// 따라서 @ControllerAdvice를 통해서 요청의 오류를 JSON으로 반환함
// *** 요청 전에 처리되는 Spring Security는 Security Filter에서 처리해야 함
// *** 우선순위를 갖고 있음, 상위 메서드에 계층 구조가 높은 예외는 맨 아래에 위치하게끔 메서드를 작성해야 함

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(exception = MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, Object> errorResponse = new HashMap<String, Object>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError)error).getField();
            String errorMessage = error.getDefaultMessage();
            errorResponse.put("field", fieldName);
            errorResponse.put("message", errorMessage);
        });

        errorResponse.put("status", HttpStatus.BAD_REQUEST.value());

        // return new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
        return ResponseEntity.badRequest().body(errorResponse);
    }
}
