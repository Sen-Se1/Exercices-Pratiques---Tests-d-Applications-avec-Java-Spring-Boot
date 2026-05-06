package com.example.testsdemo.employe;

public class EmployeNotFoundException extends RuntimeException {

    public EmployeNotFoundException(String message) {
        super(message);
    }
}