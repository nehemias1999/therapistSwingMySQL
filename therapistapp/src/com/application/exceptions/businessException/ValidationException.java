package com.application.exceptions.businessException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Para errores de validación de datos de entrada
 */
public class ValidationException extends BusinessException {
    private final Map<String, String> fieldErrors;
    
    public ValidationException(String message) {
        super(message);
        this.fieldErrors = new HashMap<>();
    }
    
    public ValidationException(String message, Map<String, String> fieldErrors) {
        super(message);
        this.fieldErrors = fieldErrors;
    }
    
    public Map<String, String> getFieldErrors() {
        return Collections.unmodifiableMap(fieldErrors);
    }
}
