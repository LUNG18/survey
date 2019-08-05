package com.gobestsoft.survey.exception;


public class BusinessException extends RuntimeException {

    public BusinessException(BizExceptionEnum bizExceptionEnum) {
        super(bizExceptionEnum.getMessage());
    }

    public BusinessException(String message) {
        super(message);
    }
}
