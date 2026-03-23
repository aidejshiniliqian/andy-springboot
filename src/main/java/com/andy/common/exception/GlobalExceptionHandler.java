package com.andy.common.exception;

import com.andy.common.result.CommonResult;
import com.andy.common.result.ResultCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public CommonResult<Void> handleBusinessException(BusinessException e, HttpServletRequest request) {
        log.error("业务异常: {} - {}", request.getRequestURI(), e.getMessage(), e);
        return CommonResult.failed(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResult<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        log.error("参数校验异常: {} - {}", request.getRequestURI(), e.getMessage());
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        return CommonResult.failed(ResultCode.VALIDATE_FAILED.getCode(), message);
    }

    @ExceptionHandler(BindException.class)
    public CommonResult<Void> handleBindException(BindException e, HttpServletRequest request) {
        log.error("参数绑定异常: {} - {}", request.getRequestURI(), e.getMessage());
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        return CommonResult.failed(ResultCode.VALIDATE_FAILED.getCode(), message);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public CommonResult<Void> handleConstraintViolationException(ConstraintViolationException e, HttpServletRequest request) {
        log.error("约束违反异常: {} - {}", request.getRequestURI(), e.getMessage());
        String message = e.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", "));
        return CommonResult.failed(ResultCode.VALIDATE_FAILED.getCode(), message);
    }

    @ExceptionHandler(Exception.class)
    public CommonResult<Void> handleException(Exception e, HttpServletRequest request) {
        log.error("系统异常: {} - {}", request.getRequestURI(), e.getMessage(), e);
        return CommonResult.failed("系统异常，请联系管理员");
    }
}
