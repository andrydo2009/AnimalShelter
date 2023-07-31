package com.coffeebreak.animalshelter.exceptions;

public class CatOwnerNotFoundException extends RuntimeException {
    public CatOwnerNotFoundException() {
        super("Хозяин кошки не найден!");
    }
}
