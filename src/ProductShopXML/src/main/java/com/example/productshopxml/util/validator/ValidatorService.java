package com.example.productshopxml.util.validator;

import jakarta.validation.ConstraintViolation;

import java.util.Set;

public interface ValidatorService {

    <E> boolean isValid(E entity);
    <E>Set <ConstraintViolation<E>> getViolation (E entity);
}
