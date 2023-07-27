package com.coffeebreak.animalshelter.exceptions;

public class DogNotFoundException extends RuntimeException {
    public DogNotFoundException() {
        super("Dog is not found!");
    }

}
