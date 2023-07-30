package com.coffeebreak.animalshelter.exceptions;

public class DogNotFoundException extends RuntimeException {
    public DogNotFoundException() {
        super("Собака не найдена!");
    }

}
