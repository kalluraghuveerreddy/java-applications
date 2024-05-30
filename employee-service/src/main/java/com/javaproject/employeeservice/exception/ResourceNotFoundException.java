package com.javaproject.employeeservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    private String fieldName;
    private String fieldValue;
    private Long fieldId;

    public ResourceNotFoundException(String fieldName, String fieldValue, Long fieldId) {
        super(String.format("%s is not found with %s : '%s'", fieldName, fieldValue, fieldId));
    }
}
