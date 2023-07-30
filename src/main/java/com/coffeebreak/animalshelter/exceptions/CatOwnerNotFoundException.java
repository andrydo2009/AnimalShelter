package com.coffeebreak.animalshelter.exceptions;

public class CatOwnerNotFoundException extends RuntimeException {

    public CatOwnerNotFoundException() {
        super("Cat owner was not found!");
    }
}
