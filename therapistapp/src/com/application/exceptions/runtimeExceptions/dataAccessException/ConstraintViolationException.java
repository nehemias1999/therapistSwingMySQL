/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.application.exceptions.runtimeExceptions.dataAccessException;

/**
 * Cuando hay violaciones de restricciones únicas (ej: duplicados)
 */
public class ConstraintViolationException extends DataAccessException {
    public ConstraintViolationException(String entityName, String constraintName) {
        super(String.format("Violación de restricción %s en entidad %s", constraintName, entityName));
    }
}
